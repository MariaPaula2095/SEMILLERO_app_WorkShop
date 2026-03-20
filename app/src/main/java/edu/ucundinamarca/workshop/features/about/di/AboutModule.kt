package edu.ucundinamarca.workshop.features.about.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucundinamarca.workshop.features.about.data.repository.AboutRepositoryImpl
import edu.ucundinamarca.workshop.features.about.domain.repository.IAboutRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AboutModule {

    @Binds
    @Singleton
    abstract fun bindAboutRepository(
        aboutRepositoryImpl: AboutRepositoryImpl
    ): IAboutRepository
}
