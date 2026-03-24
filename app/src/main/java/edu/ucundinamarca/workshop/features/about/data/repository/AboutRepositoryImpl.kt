package edu.ucundinamarca.workshop.features.about.data.repository

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import edu.ucundinamarca.workshop.features.about.data.datasource.AboutMockDataSource
import edu.ucundinamarca.workshop.features.about.domain.model.AboutInfo
import edu.ucundinamarca.workshop.features.about.domain.repository.IAboutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

/**
 * Repository implementation for About feature.
 * Currently uses Mock Data for info. To switch to Firestore:
 * 1. Uncomment the Firebase block in [getAboutInfo].
 * 2. Comment out the Mock Data block.
 */
class AboutRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    @param:ApplicationContext private val context: Context
) : IAboutRepository {

    override fun getAboutInfo(): Flow<Result<AboutInfo>> = flow {
        // --- MOCK DATA ---
        val result = try {
            val mockDataSource = AboutMockDataSource()
            val versionName = getAppVersionName()
            
            val aboutInfo = mockDataSource.getMockData().copy(
                appVersion = versionName
            )
            Result.success(aboutInfo)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Result.failure(e)
        }
        emit(result)

        /* --- FIREBASE FIRESTORE ---
        callbackFlow {
            val subscription = firestore.collection("about").document("info")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        trySend(Result.failure(error))
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        try {
                            // Mapeo manual para mayor control del esquema en Firestore
                            val devTeamData = snapshot.get("developmentTeam") as? Map<String, Any>
                            val academicData = snapshot.get("academicInfo") as? Map<String, Any>

                            val aboutInfo = AboutInfo(
                                developmentTeam = mapToSection(devTeamData),
                                academicInfo = mapToSection(academicData),
                                appVersion = getAppVersionName()
                            )
                            trySend(Result.success(aboutInfo))
                        } catch (e: Exception) {
                            trySend(Result.failure(e))
                        }
                    } else {
                        trySend(Result.failure(Exception("Documento 'about/info' no encontrado")))
                    }
                }
            awaitClose { subscription.remove() }
        }.collect { emit(it) }
        */
    }

    override suspend fun submitRating(rating: Int): Result<Unit> {
        return try {
            val ratingData = hashMapOf(
                "rating" to rating,
                "timestamp" to System.currentTimeMillis()
            )
            firestore.collection("ratings").add(ratingData).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun getAppVersionName(): String {
        return try {
            val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.packageManager.getPackageInfo(context.packageName, PackageManager.PackageInfoFlags.of(0L))
            } else {
                @Suppress("DEPRECATION")
                context.packageManager.getPackageInfo(context.packageName, 0)
            }
            packageInfo.versionName ?: "unknown"
        } catch (e: Exception) {
            "unknown"
        }
    }

}
