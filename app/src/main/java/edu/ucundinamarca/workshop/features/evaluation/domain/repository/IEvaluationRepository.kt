package edu.ucundinamarca.workshop.features.evaluation.domain.repository

import edu.ucundinamarca.workshop.features.evaluation.domain.model.EvaluationForm
import edu.ucundinamarca.workshop.features.evaluation.domain.model.EvaluationResponse
import kotlinx.coroutines.flow.Flow

interface IEvaluationRepository {
    fun getEvaluationForm(): Flow<Result<EvaluationForm>>
    suspend fun submitEvaluationResponse(response: EvaluationResponse): Result<Unit>
}