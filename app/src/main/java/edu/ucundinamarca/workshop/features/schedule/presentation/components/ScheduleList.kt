package edu.ucundinamarca.workshop.features.schedule.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.ucundinamarca.workshop.features.schedule.domain.model.ScheduleItem

@Composable
fun ScheduleList(
    items: List<ScheduleItem>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        itemsIndexed(items) { index, item ->
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically(
                    initialOffsetY = { it / 2 },
                    animationSpec = tween(
                        durationMillis = 500,
                        delayMillis = index * 80
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 500,
                        delayMillis = index * 80
                    )
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    if (index < items.lastIndex) {
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(200.dp)
                                .offset(x = 21.dp, y = 44.dp)
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                                )
                        )
                    }

                    ScheduleItemCard(
                        item = item,
                        onRegisterClick = onItemClick
                    )
                }
            }
        }

    }
}