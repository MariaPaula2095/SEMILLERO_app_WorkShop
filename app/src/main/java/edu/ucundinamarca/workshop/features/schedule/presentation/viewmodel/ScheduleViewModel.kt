package edu.ucundinamarca.workshop.features.schedule.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucundinamarca.workshop.features.schedule.domain.model.ScheduleCategory
import edu.ucundinamarca.workshop.features.schedule.domain.usecase.GetScheduleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getScheduleUseCase: GetScheduleUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScheduleUiState(isLoading = true))
    val uiState: StateFlow<ScheduleUiState> = _uiState.asStateFlow()

    init {
        fetchSchedule()
    }

    fun onCategorySelected(category: ScheduleCategory) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedCategory = category,
                filteredItems = currentState.scheduleItems.filter { it.category == category }
            )
        }
    }

    fun retry() {
        fetchSchedule()
    }

    private fun fetchSchedule() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            getScheduleUseCase().collect { result ->
                result.onSuccess { items ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            scheduleItems = items,
                            filteredItems = items.filter { it.category == currentState.selectedCategory }
                        )
                    }
                }.onFailure { error ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false, 
                            error = error.message ?: "Failed to load schedule"
                        ) 
                    }
                }
            }
        }
    }
}
