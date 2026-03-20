package edu.ucundinamarca.workshop.features.about.domain.usecase

import edu.ucundinamarca.workshop.features.about.domain.model.AboutInfo
import edu.ucundinamarca.workshop.features.about.domain.repository.IAboutRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAboutInfoUseCase @Inject constructor(
    private val repository: IAboutRepository
) {
    operator fun invoke(): Flow<Result<AboutInfo>> {
        return repository.getAboutInfo()
    }
}
