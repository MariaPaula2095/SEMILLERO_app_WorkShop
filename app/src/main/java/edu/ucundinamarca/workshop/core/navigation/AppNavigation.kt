package edu.ucundinamarca.workshop.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import edu.ucundinamarca.workshop.features.about.presentation.navigation.aboutScreen
import edu.ucundinamarca.workshop.features.about.presentation.navigation.privacyScreen
import edu.ucundinamarca.workshop.features.attendance.presentation.navigation.attendanceScreen
import edu.ucundinamarca.workshop.features.home.presentation.navigation.homeScreen
import edu.ucundinamarca.workshop.features.schedule.presentation.navigation.scheduleScreen
import edu.ucundinamarca.workshop.shared.presentation.navigation.webViewScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Home,
        modifier = modifier
    ) {
        homeScreen(
            onNavigateToForm = {
                navController.navigate(Route.Attendance)
            },
            onNavigateToSchedule = {
                navController.navigate(Route.Schedule)
            },
            onNavigateToAbout = {
                navController.navigate(Route.About)
            },
            onNavigateToWebView = { url ->
                navController.navigate(Route.WebView(url = url))
            }
        )

        attendanceScreen(
            onNavigateBack = {
                navController.popBackStack()
            },
            onNavigateToPrivacy = {
                navController.navigate(Route.Privacy)
            },
            onNavigateSuccess = {
                navController.popBackStack(Route.Home, inclusive = false)
            }
        )

        webViewScreen()

        scheduleScreen(
            onNavigateBack = {
                navController.popBackStack()
            },
            onNavigateToWebView = { url ->
                navController.navigate(Route.WebView(url = url))
            }
        )

        aboutScreen(
            onNavigateBack = {
                navController.popBackStack()
            },
            onNavigateToPrivacy = {
                navController.navigate(Route.Privacy)
            }
        )

        privacyScreen(
            onNavigateBack = {
                navController.popBackStack()
            }
        )
    }
}