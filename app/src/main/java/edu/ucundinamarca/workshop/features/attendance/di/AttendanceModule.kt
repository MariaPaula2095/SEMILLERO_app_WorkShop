package edu.ucundinamarca.workshop.features.attendance.di

import edu.ucundinamarca.workshop.features.attendance.data.repository.AttendanceRepositoryImpl
import edu.ucundinamarca.workshop.features.attendance.domain.repository.IAttendanceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AttendanceModule {

    @Binds
    @Singleton
    abstract fun bindAttendanceRepository(
        attendanceRepositoryImpl: AttendanceRepositoryImpl
    ): IAttendanceRepository
}
