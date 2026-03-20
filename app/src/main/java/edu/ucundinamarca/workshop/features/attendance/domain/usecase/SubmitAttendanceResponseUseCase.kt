package edu.ucundinamarca.workshop.features.attendance.domain.usecase

import edu.ucundinamarca.workshop.features.attendance.domain.model.AttendanceResponse
import edu.ucundinamarca.workshop.features.attendance.domain.repository.IAttendanceRepository
import javax.inject.Inject

class SubmitAttendanceResponseUseCase @Inject constructor(
    private val repository: IAttendanceRepository
) {
    suspend operator fun invoke(response: AttendanceResponse): Result<Unit> =
        repository.submitAttendanceResponse(response)
}
