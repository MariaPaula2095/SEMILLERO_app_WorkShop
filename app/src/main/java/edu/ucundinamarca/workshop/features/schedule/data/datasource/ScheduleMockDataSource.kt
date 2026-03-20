package edu.ucundinamarca.workshop.features.schedule.data.datasource

import edu.ucundinamarca.workshop.features.schedule.domain.model.ScheduleItem
import edu.ucundinamarca.workshop.features.schedule.domain.model.ScheduleCategory
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable

@Serializable
private data class ScheduleItemMock(
    val id: String,
    val startTime: String,
    val endTime: String,
    val title: String,
    val description: String,
    val speaker: String,
    val location: String,
    val type: String,
    val iconUrl: String,
    val registrationUrl: String = "",
    val category: String
)

class ScheduleMockDataSource {
    fun getMockData(): List<ScheduleItem> {
        val inputStream = javaClass.getResourceAsStream("schedule_mock.json")
            ?: throw IllegalStateException("Schedule Mock JSON not found in package")
        
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val mocks = Json.decodeFromString<List<ScheduleItemMock>>(jsonString)
        
        return mocks.map { mock ->
            ScheduleItem(
                id = mock.id,
                startTime = mock.startTime,
                endTime = mock.endTime,
                title = mock.title,
                description = mock.description,
                speaker = mock.speaker,
                location = mock.location,
                type = mock.type,
                iconUrl = mock.iconUrl,
                registrationUrl = mock.registrationUrl,
                category = ScheduleCategory.valueOf(mock.category)
            )
        }
    }
}
