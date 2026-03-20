package edu.ucundinamarca.workshop.shared.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucundinamarca.workshop.core.navigation.Route
import edu.ucundinamarca.workshop.shared.presentation.screen.WebViewScreen

fun NavGraphBuilder.webViewScreen() {
    composable<Route.WebView> { backStackEntry ->
        val webViewRoute: Route.WebView = backStackEntry.toRoute()
        WebViewScreen(url = webViewRoute.url)
    }
}
