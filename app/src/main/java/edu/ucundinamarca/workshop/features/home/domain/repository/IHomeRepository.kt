package edu.ucundinamarca.workshop.features.home.domain.repository

import edu.ucundinamarca.workshop.features.home.domain.model.HomeInfo
import kotlinx.coroutines.flow.Flow

interface IHomeRepository {
    fun getHomeInfo(): Flow<Result<HomeInfo>>
}
