package edu.ucundinamarca.workshop.features.home.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import edu.ucundinamarca.workshop.core.navigation.Route
import edu.ucundinamarca.workshop.features.home.presentation.screen.HomeScreen

fun NavGraphBuilder.homeScreen(
    onNavigateToForm: () -> Unit,
    onNavigateToSchedule: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onNavigateToWebView: (String) -> Unit,
) {
    composable<Route.Home> {
        HomeScreen(
            onNavigateToForm = onNavigateToForm,
            onNavigateToSchedule = onNavigateToSchedule,
            onNavigateToAbout = onNavigateToAbout,
            onNavigateToWebView = onNavigateToWebView,
        )
    }
}