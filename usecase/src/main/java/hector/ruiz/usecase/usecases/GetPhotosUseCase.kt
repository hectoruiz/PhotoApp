package hector.ruiz.usecase.usecases

import hector.ruiz.usecase.repositories.PhotoRepository
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(private val photoRepository: PhotoRepository) {

    suspend operator fun invoke(size: Int) =
        photoRepository.getAllPhotos(size)
}