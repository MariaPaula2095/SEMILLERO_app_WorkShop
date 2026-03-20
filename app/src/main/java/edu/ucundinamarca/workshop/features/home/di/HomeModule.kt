package edu.ucundinamarca.workshop.features.home.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucundinamarca.workshop.features.home.data.repository.HomeRepositoryImpl
import edu.ucundinamarca.workshop.features.home.domain.repository.IHomeRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeModule {

    @Binds
    @Singleton
    abstract fun bindHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): IHomeRepository
}
