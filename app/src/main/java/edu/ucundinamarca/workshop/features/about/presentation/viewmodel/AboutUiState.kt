package edu.ucundinamarca.workshop.features.about.presentation.viewmodel

import edu.ucundinamarca.workshop.features.about.domain.model.AboutInfo

data class AboutUiState(
    val isLoading: Boolean = false,
    val info: AboutInfo? = null,
    val error: String? = null,
    val isSubmittingRating: Boolean = false,
    val ratingSuccess: Boolean = false
)
