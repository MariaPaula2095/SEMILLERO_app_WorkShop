package edu.ucundinamarca.workshop.features.attendance.domain.repository

import edu.ucundinamarca.workshop.features.attendance.domain.model.AttendanceForm
import edu.ucundinamarca.workshop.features.attendance.domain.model.AttendanceResponse
import kotlinx.coroutines.flow.Flow

interface IAttendanceRepository {
    fun getAttendanceForm(): Flow<Result<AttendanceForm>>
    suspend fun submitAttendanceResponse(response: AttendanceResponse): Result<Unit>
}
