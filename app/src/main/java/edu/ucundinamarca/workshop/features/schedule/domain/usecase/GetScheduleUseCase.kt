package edu.ucundinamarca.workshop.features.schedule.domain.usecase

import edu.ucundinamarca.workshop.features.schedule.domain.model.ScheduleItem
import edu.ucundinamarca.workshop.features.schedule.domain.repository.IScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScheduleUseCase @Inject constructor(
    private val repository: IScheduleRepository
) {
    operator fun invoke(): Flow<Result<List<ScheduleItem>>> {
        return repository.getSchedule()
    }
}
