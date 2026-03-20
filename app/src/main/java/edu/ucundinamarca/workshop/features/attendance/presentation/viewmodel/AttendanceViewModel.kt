package edu.ucundinamarca.workshop.features.attendance.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucundinamarca.workshop.features.attendance.domain.model.AttendanceResponse
import edu.ucundinamarca.workshop.features.attendance.domain.usecase.GetAttendanceFormUseCase
import edu.ucundinamarca.workshop.features.attendance.domain.usecase.SubmitAttendanceResponseUseCase
import edu.ucundinamarca.workshop.features.attendance.presentation.state.AttendanceUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    private val getAttendanceFormUseCase: GetAttendanceFormUseCase,
    private val submitAttendanceResponseUseCase: SubmitAttendanceResponseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AttendanceUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadForm()
    }

    private fun loadForm() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getAttendanceFormUseCase().collect { result ->
                result.onSuccess { form ->
                    _uiState.update { 
                        it.copy(
                            form = form, 
                            isLoading = false,
                            answers = form.questions.associate { q -> q.id to "" }
                        )
                    }
                }.onFailure { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
            }
        }
    }

    fun onAnswerChange(questionId: String, answer: String) {
        _uiState.update { state ->
            val newAnswers = state.answers.toMutableMap().apply { put(questionId, answer) }
            val newErrors = state.errors.toMutableMap().apply { remove(questionId) }
            
            if (!questionId.endsWith("_other") && answer != "Otro") {
                newAnswers.remove("${questionId}_other")
                newErrors.remove("${questionId}_other")
            }
            
            state.copy(answers = newAnswers, errors = newErrors)
        }
    }

    fun onPrivacyAcceptedChange(isAccepted: Boolean) {
        _uiState.update { it.copy(isPrivacyAccepted = isAccepted) }
    }

    fun submitForm() {
        val currentState = _uiState.value
        val form = currentState.form ?: return

        // Dynamic Validation
        val errors = mutableMapOf<String, String>()
        form.questions.forEach { question ->
            val answer = currentState.answers[question.id] ?: ""
            
            // Required Check
            if (question.isRequired && answer.isBlank()) {
                errors[question.id] = "Este campo es obligatorio"
            } 
            // Custom Validation Rules
            else if (answer.isNotBlank()) {
                question.validation?.let { rule ->
                    if (rule.minLength != null && answer.length < rule.minLength) {
                        errors[question.id] = rule.errorMessage ?: "Mínimo ${rule.minLength} caracteres"
                    } else if (rule.maxLength != null && answer.length > rule.maxLength) {
                        errors[question.id] = rule.errorMessage ?: "Máximo ${rule.maxLength} caracteres"
                    } else if (rule.regex != null && !answer.matches(Regex(rule.regex))) {
                        errors[question.id] = rule.errorMessage ?: "Formato inválido"
                    }
                }
            }
            
            // Validation for "Otro" option
            if (answer == "Otro") {
                val otherAnswer = currentState.answers["${question.id}_other"]
                if (otherAnswer.isNullOrBlank()) {
                    errors["${question.id}_other"] = "Por favor especifica"
                }
            }
        }


        if (errors.isNotEmpty() || !currentState.isPrivacyAccepted) {
            _uiState.update { it.copy(errors = errors) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true) }
            
            // Data Normalization & "Otro" Merging
            val finalAnswers = mutableMapOf<String, String>()
            currentState.answers.forEach { (key, value) ->
                // Skip "_other" keys as they will be handled by their parent fields
                if (key.endsWith("_other")) return@forEach
                
                val mergedValue = if (value == "Otro") {
                    currentState.answers["${key}_other"] ?: ""
                } else {
                    value
                }
                
                // Normalization Strategy
                val normalizedValue = when (key) {
                    "institution", "academic_program" -> mergedValue.trim().uppercase()
                    else -> mergedValue.trim()
                }
                
                finalAnswers[key] = normalizedValue
            }
            
            val response = AttendanceResponse(
                formId = form.id,
                answers = finalAnswers
            )
            
            submitAttendanceResponseUseCase(response).onSuccess {
                _uiState.update { it.copy(isSubmitting = false, isSuccess = true) }
            }.onFailure { e ->
                _uiState.update { it.copy(isSubmitting = false, error = e.message) }
            }
        }
    }

    fun resetState() {
        _uiState.update { AttendanceUiState() }
        loadForm()
    }
}
