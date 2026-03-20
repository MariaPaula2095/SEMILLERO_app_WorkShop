package edu.ucundinamarca.workshop.core.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Borders(
    val none: Dp = 0.dp,
    val thin: Dp = 1.dp,
    val medium: Dp = 2.dp,
    val thick: Dp = 4.dp
)

val LocalBorders = staticCompositionLocalOf { Borders() }