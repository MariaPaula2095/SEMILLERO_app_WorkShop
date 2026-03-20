package edu.ucundinamarca.workshop.features.about.domain.repository

import edu.ucundinamarca.workshop.features.about.domain.model.AboutInfo
import kotlinx.coroutines.flow.Flow

interface IAboutRepository {
    fun getAboutInfo(): Flow<Result<AboutInfo>>
    suspend fun submitRating(rating: Int): Result<Unit>
}
