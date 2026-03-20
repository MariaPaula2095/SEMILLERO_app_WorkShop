package edu.ucundinamarca.workshop.features.schedule.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import edu.ucundinamarca.workshop.features.schedule.domain.model.ScheduleCategory

@Composable
fun CategorySelector(
    selectedCategory: ScheduleCategory,
    onCategorySelected: (ScheduleCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf(
        ScheduleCategory.CONFERENCES to "Conferencias",
        ScheduleCategory.WORKSHOPS to "Talleres",
        ScheduleCategory.VIRTUAL_WORKSHOPS to "Virtuales",
        ScheduleCategory.MARATHON to "Maratón"
    )

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { (category, label) ->
            val isSelected = category == selectedCategory
            CategoryChip(
                label = label,
                icon = getIconForCategory(category),
                isSelected = isSelected,
                onClick = { onCategorySelected(category) }
            )
        }
    }
}

@Composable
private fun CategoryChip(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    val contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        contentColor = contentColor,
        tonalElevation = if (isSelected) 4.dp else 0.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            )
        }
    }
}

private fun getIconForCategory(category: ScheduleCategory): ImageVector {
    return when (category) {
        ScheduleCategory.CONFERENCES -> Icons.Default.Groups
        ScheduleCategory.WORKSHOPS -> Icons.Default.Terminal
        ScheduleCategory.VIRTUAL_WORKSHOPS -> Icons.Default.Laptop
        ScheduleCategory.MARATHON -> Icons.Default.Code
    }
}
