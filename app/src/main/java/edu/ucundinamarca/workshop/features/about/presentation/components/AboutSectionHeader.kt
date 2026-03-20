package edu.ucundinamarca.workshop.features.about.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AboutSectionHeader(
    title: String,
    iconName: String,
    modifier: Modifier = Modifier
) {
    val icon = getIconByName(iconName)
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


@Composable
fun getIconByName(name: String): ImageVector {
    return when (name) {
        "Groups" -> Icons.Default.Groups
        "Info" -> Icons.Default.Info
        "Code" -> Icons.Default.Code
        "School" -> Icons.Default.School
        "Person" -> Icons.Default.Person
        "Engineering" -> Icons.Default.Engineering
        "CalendarMonth" -> Icons.Default.CalendarMonth
        "Book" -> Icons.Default.Book
        "AccountBalance" -> Icons.Default.AccountBalance
        else -> Icons.Default.Info
    }
}
