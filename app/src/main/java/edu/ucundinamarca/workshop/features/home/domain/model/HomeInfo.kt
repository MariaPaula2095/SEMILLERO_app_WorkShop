package edu.ucundinamarca.workshop.features.home.domain.model

data class HomeInfo(
    val bannerUrl: String,
    val logoUrl: String,
    val eventTitle: String,
    val eventDate: String,
    val startTime: String,
    val endTime: String,
    val location: String,
    val marathonLink: String,
    val universityLogoUrl: String,
    val socialLinks: SocialLinksInfo
)

data class SocialLinksInfo(
    val facebookUrl: String,
    val facebookIconUrl: String,
    val youtubeUrl: String,
    val youtubeIconUrl: String,
    val instagramUrl: String,
    val instagramIconUrl: String
)
