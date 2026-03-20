package edu.ucundinamarca.workshop.features.home.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import edu.ucundinamarca.workshop.features.home.domain.model.HomeInfo
import edu.ucundinamarca.workshop.features.home.domain.model.SocialLinksInfo
import edu.ucundinamarca.workshop.features.home.domain.repository.IHomeRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : IHomeRepository {

    override fun getHomeInfo(): Flow<Result<HomeInfo>> = flow {
        // --- MOCK DATA  ---
        try {
            val mockDataSource = edu.ucundinamarca.workshop.features.home.data.datasource.HomeMockDataSource()
            emit(Result.success(mockDataSource.getMockData()))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }

        /* --- DESCOMENTAR PARA FIREBASE FIRESTORE ---
        callbackFlow {
            val subscription = firestore.collection("home").document("info")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        trySend(Result.failure(error))
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        try {
                            val homeInfo = HomeInfo(
                                bannerUrl = snapshot.getString("bannerUrl") ?: "",
                                logoUrl = snapshot.getString("logoUrl") ?: "",
                                eventTitle = snapshot.getString("eventTitle") ?: "",
                                eventDate = snapshot.getString("eventDate") ?: "",
                                startTime = snapshot.getString("startTime") ?: "",
                                endTime = snapshot.getString("endTime") ?: "",
                                location = snapshot.getString("location") ?: "",
                                universityLogoUrl = snapshot.getString("universityLogoUrl") ?: "",
                                marathonLink = snapshot.getString("marathonLink") ?: "",
                                socialLinks = SocialLinksInfo(
                                    facebookUrl = snapshot.getString("facebookUrl") ?: "",
                                    facebookIconUrl = snapshot.getString("facebookIconUrl") ?: "",
                                    youtubeUrl = snapshot.getString("youtubeUrl") ?: "",
                                    youtubeIconUrl = snapshot.getString("youtubeIconUrl") ?: "",
                                    instagramUrl = snapshot.getString("instagramUrl") ?: "",
                                    instagramIconUrl = snapshot.getString("instagramIconUrl") ?: ""
                                )
                            )
                            trySend(Result.success(homeInfo))
                        } catch (e: Exception) {
                            trySend(Result.failure(e))
                        }
                    } else {
                        trySend(Result.failure(Exception("Documento 'home/info' no encontrado")))
                    }
                }
            awaitClose { subscription.remove() }
        }.collect { emit(it) }
        */
    }
}
