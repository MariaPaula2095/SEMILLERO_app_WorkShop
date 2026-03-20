package edu.ucundinamarca.workshop.features.home.presentation.screen

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucundinamarca.workshop.core.ui.theme.WorkshopTheme
import edu.ucundinamarca.workshop.features.home.presentation.components.ErrorComponent
import edu.ucundinamarca.workshop.features.home.presentation.components.EventDetailCard
import edu.ucundinamarca.workshop.features.home.presentation.components.HeroHeader
import edu.ucundinamarca.workshop.features.home.presentation.components.HomeShimmer
import edu.ucundinamarca.workshop.features.home.presentation.components.SocialSection
import edu.ucundinamarca.workshop.shared.presentation.components.WorkshopAppBar
import edu.ucundinamarca.workshop.features.home.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    onNavigateToForm: () -> Unit,
    onNavigateToSchedule: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onNavigateToWebView: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val spacing = WorkshopTheme.spacing
    val colorScheme = MaterialTheme.colorScheme
    val view = LocalView.current
    val context = view.context as Activity

    DisposableEffect(colorScheme.primary) {
        (context as ComponentActivity).enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                colorScheme.primary.toArgb()
            )
        )
        onDispose { }
    }

    Scaffold(
        topBar = { WorkshopAppBar() },
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->
        
        when {
            uiState.isLoading -> {
                HomeShimmer()
            }
            uiState.error != null -> {
                ErrorComponent(
                    message = if (uiState.isOffline) "No hay conexión a internet. Revisa tu red." else uiState.error!!,
                    onRetry = { viewModel.retry() }
                )
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(paddingValues)
                ) {
                    HeroHeader(
                        bannerUrl = uiState.bannerUrl,
                        logoUrl = uiState.logoUrl,
                        title = uiState.eventTitle,
                        onMarathonClick = { onNavigateToWebView(uiState.marathonLink) },
                        onRegisterClick = onNavigateToForm
                    )

                    Spacer(modifier = Modifier.height(spacing.medium))

                    SocialSection(links = uiState.socialLinks)

                    EventDetailCard(
                        date = uiState.eventDate,
                        startTime = uiState.startTime,
                        endTime = uiState.endTime,
                        location = uiState.location,
                        onScheduleClick = onNavigateToSchedule,
                        onAboutClick = onNavigateToAbout
                    )

                    Spacer(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .height(spacing.extraSmall)
                    )
                }
            }
        }
    }
}