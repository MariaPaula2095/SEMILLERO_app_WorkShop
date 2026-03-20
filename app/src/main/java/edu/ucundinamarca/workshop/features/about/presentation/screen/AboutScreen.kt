package edu.ucundinamarca.workshop.features.about.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucundinamarca.workshop.features.about.presentation.components.*
import edu.ucundinamarca.workshop.features.about.presentation.viewmodel.AboutViewModel
import edu.ucundinamarca.workshop.shared.presentation.components.WorkshopAppBar

@Composable
fun AboutScreen(
    onNavigateBack: () -> Unit,
    onNavigateToPrivacy: () -> Unit,
    viewModel: AboutViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showRatingDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.ratingSuccess) {
        if (uiState.ratingSuccess) {
            snackbarHostState.showSnackbar("¡Gracias por tu calificación!")
            viewModel.resetRatingStatus()
            showRatingDialog = false
        }
    }

    Scaffold(
        topBar = {
            WorkshopAppBar(onBackClick = onNavigateBack)
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                uiState.error != null -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.ErrorOutline,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("No se pudo cargar la información")
                        Button(onClick = { viewModel.retry() }) {
                            Text("Reintentar")
                        }
                    }
                }
                uiState.info != null -> {
                    val info = uiState.info!!
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        // Title Section
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Acerca de la App",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Text(
                                text = "Conoce a nuestro equipo y facultad",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Team Section
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            AboutSectionHeader(
                                title = info.developmentTeam.title,
                                iconName = info.developmentTeam.iconName
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            info.developmentTeam.items.forEach { item ->
                                AboutInfoCard(item = item)
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Academic Section
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            AboutSectionHeader(
                                title = info.academicInfo.title,
                                iconName = info.academicInfo.iconName
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            info.academicInfo.items.forEach { item ->
                                AboutInfoCard(item = item)
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Actions Section
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            TextButton(onClick = onNavigateToPrivacy) {
                                Text("Aviso de Privacidad")
                            }
                            
                            Button(
                                onClick = { showRatingDialog = true },
                                modifier = Modifier.fillMaxWidth(),
                                shape = MaterialTheme.shapes.medium
                            ) {
                                Text("Califícanos", fontWeight = FontWeight.Bold)
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "Versión ${info.appVersion}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        // Footer
                        Footer()
                    }
                }
            }
        }

        if (showRatingDialog) {
            RatingDialog(
                onDismiss = { showRatingDialog = false },
                onConfirm = { viewModel.submitRating(it) },
                isSubmitting = uiState.isSubmittingRating
            )
        }
    }
}
