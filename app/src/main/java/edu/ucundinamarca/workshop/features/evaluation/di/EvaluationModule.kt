package edu.ucundinamarca.workshop.features.evaluation.di

import edu.ucundinamarca.workshop.features.evaluation.data.repository.EvaluationRepositoryImpl
import edu.ucundinamarca.workshop.features.evaluation.domain.repository.IEvaluationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class EvaluationModule {

    @Binds
    @Singleton
    abstract fun bindEvaluationRepository(
        evaluationRepositoryImpl: EvaluationRepositoryImpl
    ): IEvaluationRepository
}