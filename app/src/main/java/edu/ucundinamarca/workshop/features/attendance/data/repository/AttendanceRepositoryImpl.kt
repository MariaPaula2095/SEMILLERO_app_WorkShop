package edu.ucundinamarca.workshop.features.attendance.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import edu.ucundinamarca.workshop.features.attendance.data.datasource.AttendanceMockDataSource
import edu.ucundinamarca.workshop.features.attendance.domain.model.*
import edu.ucundinamarca.workshop.features.attendance.domain.repository.IAttendanceRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class AttendanceRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : IAttendanceRepository {

    override fun getAttendanceForm(): Flow<Result<AttendanceForm>> = flow {
        // --- MOCK DATA ---
        val result = try {
            val mockDataSource = AttendanceMockDataSource()
            Result.success(mockDataSource.getMockData())
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Result.failure(e)
        }
        emit(result)

        /* --- FIREBASE FIRESTORE ---
        callbackFlow {
            val subscription = firestore.collection("attendance_forms").document("main_form")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        trySend(Result.failure(error))
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        try {
                            val questionsData = snapshot.get("questions") as? List<Map<String, Any>> ?: emptyList()
                            val questions = questionsData.map { q ->
                                AttendanceQuestion(
                                    id = q["id"] as? String ?: "",
                                    label = q["label"] as? String ?: "",
                                    type = QuestionType.valueOf(q["type"] as? String ?: "TEXT"),
                                    isRequired = q["isRequired"] as? Boolean ?: true,
                                    options = q["options"] as? List<String> ?: emptyList(),
                                    placeholder = q["placeholder"] as? String ?: ""
                                )
                            }
                            
                            val form = AttendanceForm(
                                id = snapshot.id,
                                title = snapshot.getString("title") ?: "",
                                description = snapshot.getString("description") ?: "",
                                questions = questions
                            )
                            trySend(Result.success(form))
                        } catch (e: Exception) {
                            trySend(Result.failure(e))
                        }
                    }
                }
            awaitClose { subscription.remove() }
        }.collect { emit(it) }
        */
    }

    override suspend fun submitAttendanceResponse(response: AttendanceResponse): Result<Unit> {
        return try {
            val responseData = hashMapOf(
                "formId" to response.formId,
                "answers" to response.answers,
                "timestamp" to response.timestamp.time
            )
            firestore.collection("attendance_responses").add(responseData).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
