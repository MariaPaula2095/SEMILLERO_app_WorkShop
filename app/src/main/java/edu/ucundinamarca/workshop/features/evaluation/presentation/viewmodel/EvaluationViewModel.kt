package edu.ucundinamarca.workshop.features.evaluation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucundinamarca.workshop.features.evaluation.domain.model.EvaluationResponse
import edu.ucundinamarca.workshop.features.evaluation.domain.usecase.GetEvaluationFormUseCase
import edu.ucundinamarca.workshop.features.evaluation.domain.usecase.SubmitEvaluationResponseUseCase
import edu.ucundinamarca.workshop.features.evaluation.presentation.state.EvaluationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EvaluationViewModel @Inject constructor(
    private val getEvaluationFormUseCase: GetEvaluationFormUseCase,
    private val submitEvaluationResponseUseCase: SubmitEvaluationResponseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EvaluationUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadForm()
    }

    private fun loadForm() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getEvaluationFormUseCase().collect { result ->
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

            state.copy(
                answers = newAnswers,
                errors = newErrors
            )
        }
    }

    fun submitForm() {
        val currentState = _uiState.value
        val form = currentState.form ?: return

        val errors = mutableMapOf<String, String>()

        form.questions.forEach { question ->
            val answer = currentState.answers[question.id] ?: ""

            if (question.isRequired && answer.isBlank()) {
                errors[question.id] = "Este campo es obligatorio"
            }
        }

        if (errors.isNotEmpty()) {
            _uiState.update { it.copy(errors = errors) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true) }

            val finalAnswers = currentState.answers.mapValues { it.value.trim() }

            val response = EvaluationResponse(
                formId = form.id,
                answers = finalAnswers
            )

            submitEvaluationResponseUseCase(response).onSuccess {
                _uiState.update {
                    it.copy(
                        isSubmitting = false,
                        isSuccess = true
                    )
                }
            }.onFailure { e ->
                _uiState.update {
                    it.copy(
                        isSubmitting = false,
                        error = e.message
                    )
                }
            }
        }
    }

    fun resetState() {
        _uiState.update { EvaluationUiState() }
        loadForm()
    }
}