package edu.ucundinamarca.workshop.features.home.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import edu.ucundinamarca.workshop.core.ui.theme.WorkshopTheme

@Composable
fun EventDetailCard(
    date: String,
    startTime: String,
    endTime: String,
    location: String,
    onScheduleClick: () -> Unit,
    onAboutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = WorkshopTheme.spacing
    val elevations = WorkshopTheme.elevations

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(spacing.medium),
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.cardElevation(defaultElevation = elevations.level2),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(spacing.medium)
            ) {
                Text(
                    text = "Detalles del Evento",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Column(
                modifier = Modifier
                    .padding(spacing.medium)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DetailItem(
                        icon = Icons.Default.DateRange,
                        value = date,
                        modifier = Modifier.weight(1f)
                    )
                    
                    Spacer(
                        modifier = Modifier
                            .width(1.dp)
                            .height(40.dp)
                            .background(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    )
                    
                    DetailItem(
                        icon = Icons.Default.Info,
                        value = "$startTime\n$endTime",
                        modifier = Modifier.weight(1f).padding(start = spacing.medium)
                    )
                }

                Spacer(modifier = Modifier.height(spacing.medium))

                DetailItem(
                    icon = Icons.Default.LocationOn,
                    value = location
                )

                Spacer(modifier = Modifier.height(spacing.large))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(spacing.small)
                ) {
                        Button(
                        onClick = onScheduleClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                                shape = MaterialTheme.shapes.medium
                    ) {
                        Text("Cronograma", style = MaterialTheme.typography.labelMedium)
                    }

                    OutlinedButton(
                        onClick = onAboutClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text("Acerca de", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailItem(
    icon: ImageVector,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(vertical = WorkshopTheme.spacing.extraSmall)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),
                    shape = MaterialTheme.shapes.small
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(WorkshopTheme.iconography.medium)
            )
        }
        Spacer(modifier = Modifier.width(WorkshopTheme.spacing.medium))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}
