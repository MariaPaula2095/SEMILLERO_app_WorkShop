package edu.ucundinamarca.workshop.features.schedule.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import edu.ucundinamarca.workshop.core.navigation.Route
import edu.ucundinamarca.workshop.features.schedule.presentation.screen.ScheduleScreen

fun NavGraphBuilder.scheduleScreen(
    onNavigateBack: () -> Unit,
    onNavigateToWebView: (String) -> Unit
) {
    composable<Route.Schedule> {
        ScheduleScreen(
            onNavigateBack = onNavigateBack,
            onNavigateToWebView = onNavigateToWebView
        )
    }
}
