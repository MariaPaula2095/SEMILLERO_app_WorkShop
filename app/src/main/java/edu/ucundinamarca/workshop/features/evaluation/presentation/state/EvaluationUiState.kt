package edu.ucundinamarca.workshop.features.evaluation.presentation.state

import edu.ucundinamarca.workshop.features.evaluation.domain.model.EvaluationForm

data class EvaluationUiState(
    val isLoading: Boolean = false,
    val form: EvaluationForm? = null,
    val answers: Map<String, String> = emptyMap(),
    val errors: Map<String, String> = emptyMap(),
    val isSubmitting: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)