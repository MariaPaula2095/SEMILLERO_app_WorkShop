package edu.ucundinamarca.workshop.features.schedule.presentation.viewmodel

import edu.ucundinamarca.workshop.features.schedule.domain.model.ScheduleCategory
import edu.ucundinamarca.workshop.features.schedule.domain.model.ScheduleItem

data class ScheduleUiState(
    val isLoading: Boolean = false,
    val scheduleItems: List<ScheduleItem> = emptyList(),
    val filteredItems: List<ScheduleItem> = emptyList(),
    val selectedCategory: ScheduleCategory = ScheduleCategory.CONFERENCES,
    val error: String? = null
)
