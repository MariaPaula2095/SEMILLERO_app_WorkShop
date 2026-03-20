package edu.ucundinamarca.workshop.features.schedule.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import edu.ucundinamarca.workshop.features.schedule.data.datasource.ScheduleMockDataSource
import edu.ucundinamarca.workshop.features.schedule.domain.model.ScheduleCategory
import edu.ucundinamarca.workshop.features.schedule.domain.model.ScheduleItem
import edu.ucundinamarca.workshop.features.schedule.domain.repository.IScheduleRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
class ScheduleRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : IScheduleRepository {

    override fun getSchedule(): Flow<Result<List<ScheduleItem>>> = flow {
        // --- MOCK DATA ---
        try {
            val mockDataSource = ScheduleMockDataSource()
            emit(Result.success(mockDataSource.getMockData()))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }

        /* --- FIREBASE FIRESTORE ---
        callbackFlow {
            val subscription = firestore.collection("schedule")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        trySend(Result.failure(error))
                        return@addSnapshotListener
                    }

                    if (snapshot != null) {
                        try {
                            val items = snapshot.documents.mapNotNull { doc ->
                                ScheduleItem(
                                    id = doc.id,
                                    startTime = doc.getString("startTime") ?: "",
                                    endTime = doc.getString("endTime") ?: "",
                                    title = doc.getString("title") ?: "",
                                    description = doc.getString("description") ?: "",
                                    speaker = doc.getString("speaker") ?: "",
                                    location = doc.getString("location") ?: "",
                                    type = doc.getString("type") ?: "",
                                    iconUrl = doc.getString("iconUrl") ?: "",
                                    registrationUrl = doc.getString("registrationUrl") ?: "",
                                    category = ScheduleCategory.valueOf(doc.getString("category") ?: "CONFERENCES")
                                )
                            }
                            trySend(Result.success(items))
                        } catch (e: Exception) {
                            trySend(Result.failure(e))
                        }
                    }
                }
            awaitClose { subscription.remove() }
        }.collect { emit(it) }
        */
    }
}
