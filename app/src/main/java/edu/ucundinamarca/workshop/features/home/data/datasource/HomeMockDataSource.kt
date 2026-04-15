package edu.ucundinamarca.workshop.features.home.data.datasource

import edu.ucundinamarca.workshop.features.home.domain.model.HomeInfo
import edu.ucundinamarca.workshop.features.home.domain.model.SocialLinksInfo
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
private data class HomeInfoMock(
    val bannerUrl: String,
    val logoUrl: String,
    val eventTitle: String,
    val eventDate: String,
    val startTime: String,
    val endTime: String,
    val location: String,
    val universityLogoUrl: String,
    val marathonLink: String,
    val facebookUrl: String,
    val facebookIconUrl: String,
    val youtubeUrl: String,
    val youtubeIconUrl: String,
    val instagramUrl: String,
    val instagramIconUrl: String
)

class HomeMockDataSource @Inject constructor() {
    fun getMockData(): HomeInfo {
        val inputStream = javaClass.getResourceAsStream("home_info_mock.json")
            ?: throw IllegalStateException("Mock JSON not found in package")
        
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val mock = Json.decodeFromString<HomeInfoMock>(jsonString)
        
        return HomeInfo(
            bannerUrl = mock.bannerUrl,
            logoUrl = mock.logoUrl,
            eventTitle = mock.eventTitle,
            eventDate = mock.eventDate,
            startTime = mock.startTime,
            endTime = mock.endTime,
            location = mock.location,
            universityLogoUrl = mock.universityLogoUrl,
            marathonLink = mock.marathonLink,
            socialLinks = SocialLinksInfo(
                facebookUrl = mock.facebookUrl,
                facebookIconUrl = mock.facebookIconUrl,
                youtubeUrl = mock.youtubeUrl,
                youtubeIconUrl = mock.youtubeIconUrl,
                instagramUrl = mock.instagramUrl,
                instagramIconUrl = mock.instagramIconUrl
            )
        )
    }
}
