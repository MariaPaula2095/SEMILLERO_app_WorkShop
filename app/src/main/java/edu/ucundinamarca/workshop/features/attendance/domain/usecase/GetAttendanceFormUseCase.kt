package edu.ucundinamarca.workshop.features.attendance.domain.usecase

import edu.ucundinamarca.workshop.features.attendance.domain.model.AttendanceForm
import edu.ucundinamarca.workshop.features.attendance.domain.repository.IAttendanceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAttendanceFormUseCase @Inject constructor(
    private val repository: IAttendanceRepository
) {
    operator fun invoke(): Flow<Result<AttendanceForm>> = repository.getAttendanceForm()
}
