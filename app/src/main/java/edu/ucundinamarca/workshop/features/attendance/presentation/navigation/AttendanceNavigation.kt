package edu.ucundinamarca.workshop.features.attendance.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import edu.ucundinamarca.workshop.core.navigation.Route
import edu.ucundinamarca.workshop.features.attendance.presentation.screen.AttendanceScreen

fun NavGraphBuilder.attendanceScreen(
    onNavigateBack: () -> Unit,
    onNavigateToPrivacy: () -> Unit,
    onNavigateSuccess: () -> Unit
) {
    composable<Route.Attendance> {
        AttendanceScreen(
            onBack = onNavigateBack,
            onNavigateToPrivacy = onNavigateToPrivacy,
            onSuccess = onNavigateSuccess
        )
    }
}
