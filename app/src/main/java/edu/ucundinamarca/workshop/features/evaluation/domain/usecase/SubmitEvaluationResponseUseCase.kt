package edu.ucundinamarca.workshop.features.evaluation.domain.usecase

import edu.ucundinamarca.workshop.features.evaluation.domain.model.EvaluationResponse
import edu.ucundinamarca.workshop.features.evaluation.domain.repository.IEvaluationRepository
import javax.inject.Inject

class SubmitEvaluationResponseUseCase @Inject constructor(
    private val repository: IEvaluationRepository
) {
    suspend operator fun invoke(response: EvaluationResponse): Result<Unit> =
        repository.submitEvaluationResponse(response)
}