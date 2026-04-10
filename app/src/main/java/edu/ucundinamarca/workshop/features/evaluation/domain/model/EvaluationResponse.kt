package edu.ucundinamarca.workshop.features.evaluation.domain.model

import java.util.Date

data class EvaluationResponse(
    val formId: String,
    val answers: Map<String, String>,
    val timestamp: Date = Date()
)