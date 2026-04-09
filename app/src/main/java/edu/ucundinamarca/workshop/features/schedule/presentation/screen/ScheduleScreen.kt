package edu.ucundinamarca.workshop.features.schedule.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucundinamarca.workshop.features.schedule.presentation.components.CategorySelector
import edu.ucundinamarca.workshop.features.schedule.presentation.components.ScheduleList
import edu.ucundinamarca.workshop.features.schedule.presentation.viewmodel.ScheduleViewModel
import edu.ucundinamarca.workshop.shared.presentation.components.WorkshopAppBar
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.key
import edu.ucundinamarca.workshop.features.about.presentation.components.Footer
@Composable
fun ScheduleScreen(
    onNavigateBack: () -> Unit,
    onNavigateToWebView: (String) -> Unit,
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            WorkshopAppBar(onBackClick = onNavigateBack)
        },
        bottomBar = {
            Footer()
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Header Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Cronograma",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Surface(
                    modifier = Modifier
                        .width(60.dp)
                        .height(4.dp)
                        .padding(top = 4.dp),
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(50)
                ) { }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Explora las actividades del Workshop",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            CategorySelector(
                selectedCategory = uiState.selectedCategory,
                onCategorySelected = { viewModel.onCategorySelected(it) }
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    uiState.isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    uiState.error != null -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = androidx.compose.material.icons.Icons.Default.ErrorOutline,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "¡Ups! Algo salió mal",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = uiState.error ?: "Ocurrió un error inesperado",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 32.dp)
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Button(onClick = { viewModel.retry() }) {
                                Text("Reintentar")
                            }
                        }
                    }
                    uiState.filteredItems.isEmpty() -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = androidx.compose.material.icons.Icons.Default.EventBusy,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "No hay actividades",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Aún no hay eventos registrados en esta categoría.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    else -> {
                        AnimatedContent(
                            targetState = uiState.selectedCategory,
                            transitionSpec = {
                                (
                                        slideInHorizontally(
                                            initialOffsetX = { fullWidth -> fullWidth },
                                            animationSpec = tween(400)
                                        ) + fadeIn(animationSpec = tween(400))
                                        ).togetherWith(
                                        slideOutHorizontally(
                                            targetOffsetX = { fullWidth -> -fullWidth },
                                            animationSpec = tween(400)
                                        ) + fadeOut(animationSpec = tween(400))
                                    )
                            },
                            label = "CategoryAnimation"
                        ) { targetCategory ->
                            key(targetCategory) {
                                ScheduleList(
                                    items = uiState.filteredItems,
                                    onItemClick = onNavigateToWebView
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}
