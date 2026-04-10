package edu.ucundinamarca.workshop.features.evaluation.data.datasource

import edu.ucundinamarca.workshop.features.evaluation.domain.model.EvaluationForm
import edu.ucundinamarca.workshop.features.evaluation.domain.model.EvaluationQuestion
import edu.ucundinamarca.workshop.features.evaluation.domain.model.QuestionType
import edu.ucundinamarca.workshop.features.evaluation.domain.model.ValidationRule
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
private data class EvaluationFormMock(
    val id: String,
    val title: String,
    val description: String,
    val questions: List<EvaluationQuestionMock>
)

@Serializable
private data class EvaluationQuestionMock(
    val id: String,
    val label: String,
    val type: String,
    val isRequired: Boolean = true,
    val options: List<String> = emptyList(),
    val placeholder: String = "",
    val validation: ValidationRuleMock? = null
)

@Serializable
private data class ValidationRuleMock(
    val minLength: Int? = null,
    val maxLength: Int? = null,
    val regex: String? = null,
    val errorMessage: String? = null
)

class EvaluationMockDataSource {
    private val json = Json { ignoreUnknownKeys = true }

    fun getMockData(): EvaluationForm {
        val inputStream = javaClass.getResourceAsStream("evaluation_mock.json")
            ?: throw IllegalStateException("Evaluation Mock JSON not found")

        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val mock = json.decodeFromString<EvaluationFormMock>(jsonString)

        return EvaluationForm(
            id = mock.id,
            title = mock.title,
            description = mock.description,
            questions = mock.questions.map { q ->
                EvaluationQuestion(
                    id = q.id,
                    label = q.label,
                    type = QuestionType.valueOf(q.type),
                    isRequired = q.isRequired,
                    options = q.options,
                    placeholder = q.placeholder,
                    validation = q.validation?.let { v ->
                        ValidationRule(
                            minLength = v.minLength,
                            maxLength = v.maxLength,
                            regex = v.regex,
                            errorMessage = v.errorMessage
                        )
                    }
                )
            }
        )
    }
}