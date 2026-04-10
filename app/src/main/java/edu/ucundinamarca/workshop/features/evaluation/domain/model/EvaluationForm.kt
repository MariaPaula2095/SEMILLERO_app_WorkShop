package edu.ucundinamarca.workshop.features.evaluation.domain.model

enum class QuestionType {
    TEXT,
    EMAIL,
    NUMBER,
    RADIO_GROUP,
    DROPDOWN
}

data class ValidationRule(
    val minLength: Int? = null,
    val maxLength: Int? = null,
    val regex: String? = null,
    val errorMessage: String? = null
)

data class EvaluationQuestion(
    val id: String,
    val label: String,
    val type: QuestionType,
    val isRequired: Boolean = true,
    val options: List<String> = emptyList(),
    val placeholder: String = "",
    val validation: ValidationRule? = null
)

data class EvaluationForm(
    val id: String,
    val title: String,
    val description: String,
    val questions: List<EvaluationQuestion>
)