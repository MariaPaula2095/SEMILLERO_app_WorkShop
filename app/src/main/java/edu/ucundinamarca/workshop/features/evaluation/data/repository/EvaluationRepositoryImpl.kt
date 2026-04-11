package edu.ucundinamarca.workshop.features.evaluation.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import edu.ucundinamarca.workshop.features.evaluation.data.datasource.EvaluationMockDataSource
import edu.ucundinamarca.workshop.features.evaluation.domain.model.EvaluationForm
import edu.ucundinamarca.workshop.features.evaluation.domain.model.EvaluationResponse
import edu.ucundinamarca.workshop.features.evaluation.domain.repository.IEvaluationRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EvaluationRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : IEvaluationRepository {

    override fun getEvaluationForm(): Flow<Result<EvaluationForm>> = flow {
        val result = try {
            val mockDataSource = EvaluationMockDataSource()
            Result.success(mockDataSource.getMockData())
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Result.failure(e)
        }
        emit(result)
    }

    override suspend fun submitEvaluationResponse(response: EvaluationResponse): Result<Unit> {
        return try {
            val responseData = hashMapOf(
                "formId" to response.formId,
                "answers" to response.answers,
                "timestamp" to response.timestamp.time
            )
            firestore.collection("evaluation_responses").add(responseData).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}