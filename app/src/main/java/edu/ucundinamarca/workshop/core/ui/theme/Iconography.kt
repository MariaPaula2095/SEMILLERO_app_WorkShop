package edu.ucundinamarca.workshop.core.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Iconography(
    val extraSmall: Dp = 12.dp,
    val small: Dp = 18.dp,
    val medium: Dp = 24.dp,
    val large: Dp = 32.dp,
    val extraLarge: Dp = 48.dp
)

val LocalIconography = staticCompositionLocalOf { Iconography() }