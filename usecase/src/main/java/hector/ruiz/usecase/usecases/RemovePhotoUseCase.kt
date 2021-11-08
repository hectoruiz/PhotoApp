package hector.ruiz.usecase.usecases

import hector.ruiz.usecase.repositories.PhotoRepository
import javax.inject.Inject

class RemovePhotoUseCase @Inject constructor(private val photoRepository: PhotoRepository) {

    suspend operator fun invoke(photoId: Int) =
        photoRepository.removePhoto(photoId)
}