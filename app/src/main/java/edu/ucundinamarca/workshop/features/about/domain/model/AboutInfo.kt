package edu.ucundinamarca.workshop.features.about.domain.model

data class AboutInfo(
    val developmentTeam: Section,
    val academicInfo: Section,
    val appVersion: String,
    val iconCredits: Section
)

data class Section(
    val title: String,
    val iconName: String,
    val items: List<InfoItem>
)

data class InfoItem(
    val title: String,
    val content: List<String>,
    val iconName: String
)
