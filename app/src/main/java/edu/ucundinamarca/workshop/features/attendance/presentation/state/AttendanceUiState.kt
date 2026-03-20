package edu.ucundinamarca.workshop.features.attendance.presentation.state

import edu.ucundinamarca.workshop.features.attendance.domain.model.AttendanceForm

data class AttendanceUiState(
    val isLoading: Boolean = false,
    val form: AttendanceForm? = null,
    val answers: Map<String, String> = emptyMap(), // QuestionID -> Input
    val errors: Map<String, String> = emptyMap(),   // QuestionID -> ErrorMsg
    val isSubmitting: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val isPrivacyAccepted: Boolean = false
)
