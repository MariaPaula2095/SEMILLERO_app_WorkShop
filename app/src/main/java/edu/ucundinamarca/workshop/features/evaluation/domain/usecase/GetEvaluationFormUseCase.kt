package edu.ucundinamarca.workshop.features.evaluation.domain.usecase

import edu.ucundinamarca.workshop.features.evaluation.domain.model.EvaluationForm
import edu.ucundinamarca.workshop.features.evaluation.domain.repository.IEvaluationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEvaluationFormUseCase @Inject constructor(
    private val repository: IEvaluationRepository
) {
    operator fun invoke(): Flow<Result<EvaluationForm>> = repository.getEvaluationForm()
}