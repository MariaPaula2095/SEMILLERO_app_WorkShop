package edu.ucundinamarca.workshop.features.home.domain.usecase

import edu.ucundinamarca.workshop.features.home.domain.model.HomeInfo
import edu.ucundinamarca.workshop.features.home.domain.repository.IHomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHomeInfoUseCase @Inject constructor(
    private val repository: IHomeRepository
) {
    operator fun invoke(): Flow<Result<HomeInfo>> {
        return repository.getHomeInfo()
    }
}
