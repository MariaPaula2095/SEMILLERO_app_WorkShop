package edu.ucundinamarca.workshop.features.ai_chat.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import edu.ucundinamarca.workshop.core.navigation.Route
import edu.ucundinamarca.workshop.features.ai_chat.presentation.screen.AiChatScreen

fun NavGraphBuilder.aiChatScreen(
    onNavigateBack: () -> Unit
) {
    composable<Route.AiChat> {
        AiChatScreen()
    }
}
