package edu.ucundinamarca.workshop.features.schedule.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucundinamarca.workshop.features.schedule.data.repository.ScheduleRepositoryImpl
import edu.ucundinamarca.workshop.features.schedule.domain.repository.IScheduleRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ScheduleModule {

    @Binds
    @Singleton
    abstract fun bindScheduleRepository(
        scheduleRepositoryImpl: ScheduleRepositoryImpl
    ): IScheduleRepository
}
