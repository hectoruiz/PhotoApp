package hector.ruiz.usecase.usecases

import hector.ruiz.domain.PhotoUi
import hector.ruiz.usecase.repositories.PhotoRepository
import javax.inject.Inject

class AddPhotoUseCase @Inject constructor(private val photoRepository: PhotoRepository) {

    suspend operator fun invoke(photo: PhotoUi) =
        photoRepository.addPhoto(photo)
}
