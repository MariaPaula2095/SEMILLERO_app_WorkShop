package edu.ucundinamarca.workshop.features.home.presentation.viewmodel

data class HomeUiState(
    val isLoading: Boolean = false,
    val isOffline: Boolean = false,
    val bannerUrl: String = "",
    val logoUrl: String = "",
    val eventTitle: String = "",
    val eventDate: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val location: String = "",
    val marathonLink: String = "",
    val socialLinks: SocialLinks = SocialLinks(),
    val error: String? = null
)

data class SocialLinks(
    val facebookUrl: String = "",
    val facebookIconUrl: String = "",
    val youtubeUrl: String = "",
    val youtubeIconUrl: String = "",
    val instagramUrl: String = "",
    val instagramIconUrl: String = ""
)