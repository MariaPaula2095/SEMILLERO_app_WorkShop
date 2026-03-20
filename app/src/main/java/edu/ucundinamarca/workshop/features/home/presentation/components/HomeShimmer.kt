package edu.ucundinamarca.workshop.features.home.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import edu.ucundinamarca.workshop.core.ui.theme.WorkshopTheme
import edu.ucundinamarca.workshop.core.ui.utils.shimmerEffect

@Composable
fun HomeShimmer() {
    val spacing = WorkshopTheme.spacing
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(modifier = Modifier.padding(bottom = spacing.medium)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .shimmerEffect()
            )
            
            Row(
                modifier = Modifier
                    .padding(spacing.medium)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing.small)
            ) {
                Box(modifier = Modifier.weight(1f).height(48.dp).clip(MaterialTheme.shapes.medium).shimmerEffect())
                Box(modifier = Modifier.weight(1f).height(48.dp).clip(MaterialTheme.shapes.medium).shimmerEffect())
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.medium),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.width(200.dp).height(24.dp).clip(RoundedCornerShape(4.dp)).shimmerEffect())
            Spacer(modifier = Modifier.height(spacing.medium))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                repeat(3) {
                    Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                        Box(modifier = Modifier.size(WorkshopTheme.iconography.extraLarge).clip(CircleShape).shimmerEffect())
                        Spacer(modifier = Modifier.height(spacing.extraSmall))
                        Box(modifier = Modifier.width(60.dp).height(14.dp).clip(RoundedCornerShape(2.dp)).shimmerEffect())
                    }
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.medium),
            shape = MaterialTheme.shapes.extraLarge,
            elevation = CardDefaults.cardElevation(defaultElevation = WorkshopTheme.elevations.level2)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.fillMaxWidth().height(56.dp).shimmerEffect())
                
                Column(modifier = Modifier.padding(spacing.medium)) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        repeat(2) {
                            Row(modifier = Modifier.weight(1f), verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(40.dp).clip(MaterialTheme.shapes.small).shimmerEffect())
                                Spacer(modifier = Modifier.width(spacing.medium))
                                Box(modifier = Modifier.width(80.dp).height(16.dp).clip(RoundedCornerShape(2.dp)).shimmerEffect())
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(spacing.medium))
                    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(40.dp).clip(MaterialTheme.shapes.small).shimmerEffect())
                        Spacer(modifier = Modifier.width(spacing.medium))
                        Box(modifier = Modifier.width(200.dp).height(16.dp).clip(RoundedCornerShape(2.dp)).shimmerEffect())
                    }
                    Spacer(modifier = Modifier.height(spacing.large))
                    Row(horizontalArrangement = Arrangement.spacedBy(spacing.small)) {
                        Box(modifier = Modifier.weight(1f).height(48.dp).clip(MaterialTheme.shapes.medium).shimmerEffect())
                        Box(modifier = Modifier.weight(1f).height(48.dp).clip(MaterialTheme.shapes.medium).shimmerEffect())
                    }
                }
            }
        }
    }
}
