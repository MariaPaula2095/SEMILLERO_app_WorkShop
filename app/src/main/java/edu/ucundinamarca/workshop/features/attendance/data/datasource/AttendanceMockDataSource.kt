package edu.ucundinamarca.workshop.features.attendance.data.datasource

import edu.ucundinamarca.workshop.features.attendance.domain.model.AttendanceForm
import edu.ucundinamarca.workshop.features.attendance.domain.model.AttendanceQuestion
import edu.ucundinamarca.workshop.features.attendance.domain.model.QuestionType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
private data class AttendanceFormMock(
    val id: String,
    val title: String,
    val description: String,
    val questions: List<AttendanceQuestionMock>
)

@Serializable
private data class AttendanceQuestionMock(
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

class AttendanceMockDataSource {
    private val json = Json { ignoreUnknownKeys = true }

    fun getMockData(): AttendanceForm {
        val inputStream = javaClass.getResourceAsStream("attendance_mock.json")
            ?: throw IllegalStateException("Attendance Mock JSON not found")
        
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val mock = json.decodeFromString<AttendanceFormMock>(jsonString)
        
        return AttendanceForm(
            id = mock.id,
            title = mock.title,
            description = mock.description,
            questions = mock.questions.map { q ->
                AttendanceQuestion(
                    id = q.id,
                    label = q.label,
                    type = QuestionType.valueOf(q.type),
                    isRequired = q.isRequired,
                    options = q.options,
                    placeholder = q.placeholder,
                    validation = q.validation?.let { v ->
                        edu.ucundinamarca.workshop.features.attendance.domain.model.ValidationRule(
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
