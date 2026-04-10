package edu.ucundinamarca.workshop.core.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.ucundinamarca.workshop.features.about.presentation.navigation.aboutScreen
import edu.ucundinamarca.workshop.features.about.presentation.navigation.privacyScreen
import edu.ucundinamarca.workshop.features.attendance.presentation.navigation.attendanceScreen
import edu.ucundinamarca.workshop.features.home.presentation.navigation.homeScreen
import edu.ucundinamarca.workshop.features.schedule.presentation.navigation.scheduleScreen
import edu.ucundinamarca.workshop.shared.presentation.navigation.webViewScreen
import edu.ucundinamarca.workshop.features.ai_chat.presentation.navigation.aiChatScreen
import edu.ucundinamarca.workshop.features.evaluation.presentation.screen.EvaluacionWorkshopScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Home,
        modifier = modifier,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            )
        }
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
            },
            onNavigateToAiChat = {
                navController.navigate(Route.AiChat)
            },
            // se agrega ruta de evaluacion
            onNavigateToEvaluation = {
                navController.navigate(Route.EvaluationWorkshop)
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

        aiChatScreen(
            onNavigateBack = {
                navController.popBackStack()
            }
        )

        composable<Route.EvaluationWorkshop> {
            EvaluacionWorkshopScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

    }
}