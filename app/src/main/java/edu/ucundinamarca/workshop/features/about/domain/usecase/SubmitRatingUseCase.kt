package edu.ucundinamarca.workshop.features.about.domain.usecase

import edu.ucundinamarca.workshop.features.about.domain.repository.IAboutRepository
import javax.inject.Inject

class SubmitRatingUseCase @Inject constructor(
    private val repository: IAboutRepository
) {
    suspend operator fun invoke(rating: Int): Result<Unit> {
        return repository.submitRating(rating)
    }
}
