package edu.ucundinamarca.workshop.features.about.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucundinamarca.workshop.features.about.domain.usecase.GetAboutInfoUseCase
import edu.ucundinamarca.workshop.features.about.domain.usecase.SubmitRatingUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val getAboutInfoUseCase: GetAboutInfoUseCase,
    private val submitRatingUseCase: SubmitRatingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AboutUiState())
    val uiState: StateFlow<AboutUiState> = _uiState.asStateFlow()

    init {
        loadAboutInfo()
    }

    private fun loadAboutInfo() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getAboutInfoUseCase().collect { result ->
                result.onSuccess { info ->
                    _uiState.update { it.copy(isLoading = false, info = info, error = null) }
                }.onFailure { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
            }
        }
    }

    fun submitRating(rating: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSubmittingRating = true) }
            val result = submitRatingUseCase(rating)
            result.onSuccess {
                _uiState.update { it.copy(isSubmittingRating = false, ratingSuccess = true) }
            }.onFailure { e ->
                _uiState.update { it.copy(isSubmittingRating = false, error = e.message) }
            }
        }
    }

    fun resetRatingStatus() {
        _uiState.update { it.copy(ratingSuccess = false) }
    }

    fun retry() {
        loadAboutInfo()
    }
}
