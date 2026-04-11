package edu.ucundinamarca.workshop.features.welcome.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import edu.ucundinamarca.workshop.core.navigation.Route
import edu.ucundinamarca.workshop.features.welcome.presentation.screen.WelcomeScreen

fun NavGraphBuilder.welcomeScreen(
    onNavigateToHome: () -> Unit
) {
    composable<Route.Welcome> {
        WelcomeScreen(
            onNavigateToHome = onNavigateToHome
        )
    }
}