package edu.ucundinamarca.workshop.features.about.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import edu.ucundinamarca.workshop.core.navigation.Route
import edu.ucundinamarca.workshop.features.about.presentation.screen.AboutScreen
import edu.ucundinamarca.workshop.shared.presentation.screen.PrivacyScreen

fun NavGraphBuilder.aboutScreen(
    onNavigateBack: () -> Unit,
    onNavigateToPrivacy: () -> Unit
) {
    composable<Route.About> {
        AboutScreen(
            onNavigateBack = onNavigateBack,
            onNavigateToPrivacy = onNavigateToPrivacy
        )
    }
}

fun NavGraphBuilder.privacyScreen(
    onNavigateBack: () -> Unit
) {
    composable<Route.Privacy> {
        PrivacyScreen(
            onNavigateBack = onNavigateBack
        )
    }
}
