package edu.ucundinamarca.workshop.features.schedule.domain.repository

import edu.ucundinamarca.workshop.features.schedule.domain.model.ScheduleItem
import kotlinx.coroutines.flow.Flow

interface IScheduleRepository {
    fun getSchedule(): Flow<Result<List<ScheduleItem>>>
}
