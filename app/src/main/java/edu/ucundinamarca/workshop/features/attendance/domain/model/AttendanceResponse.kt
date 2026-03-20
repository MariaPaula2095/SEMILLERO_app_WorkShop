package edu.ucundinamarca.workshop.features.attendance.domain.model

import java.util.Date

data class AttendanceResponse(
    val formId: String,
    val answers: Map<String, String>,
    val timestamp: Date = Date()
)
