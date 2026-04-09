package edu.ucundinamarca.workshop.features.about.data.datasource

import edu.ucundinamarca.workshop.features.about.domain.model.AboutInfo
import edu.ucundinamarca.workshop.features.about.domain.model.InfoItem
import edu.ucundinamarca.workshop.features.about.domain.model.Section
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable

@Serializable
private data class AboutInfoMock(
    val developmentTeam: SectionMock,
    val academicInfo: SectionMock,
    val iconCredits: SectionMock
)

@Serializable
private data class SectionMock(
    val title: String,
    val iconName: String,
    val items: List<InfoItemMock>
)

@Serializable
private data class InfoItemMock(
    val title: String,
    val content: List<String>,
    val iconName: String
)

class AboutMockDataSource {
    fun getMockData(): AboutInfo {
        val inputStream = javaClass.getResourceAsStream("about_mock.json")
            ?: throw IllegalStateException("About Mock JSON not found in package")
        
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val mock = Json.decodeFromString<AboutInfoMock>(jsonString)
        
        return AboutInfo(
            developmentTeam = mapSection(mock.developmentTeam),
            academicInfo = mapSection(mock.academicInfo),
            iconCredits = mapSection(mock.iconCredits),
            appVersion = ""
        )
    }

    private fun mapSection(mock: SectionMock): Section {
        return Section(
            title = mock.title,
            iconName = mock.iconName,
            items = mock.items.map { 
                InfoItem(it.title, it.content, it.iconName)
            }
        )
    }
}
