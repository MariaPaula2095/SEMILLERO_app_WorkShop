package edu.ucundinamarca.workshop.features.schedule.domain.model

data class ScheduleItem(
    val id: String = "",
    val startTime: String,
    val endTime: String,
    val title: String,
    val description: String,
    val speaker: String,
    val location: String,
    val type: String,
    val iconUrl: String,
    val registrationUrl: String = "",
    val category: ScheduleCategory
)
