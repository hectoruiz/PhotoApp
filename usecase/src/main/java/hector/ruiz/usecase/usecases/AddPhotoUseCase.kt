package hector.ruiz.usecase.usecases

import hector.ruiz.usecase.repositories.PhotoRepository
import javax.inject.Inject

class AddPhotoUseCase @Inject constructor(private val photoRepository: PhotoRepository) {

    suspend operator fun invoke(photoPath: String) =
        photoRepository.addPhoto(photoPath)
}
