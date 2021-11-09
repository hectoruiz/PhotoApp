package hector.ruiz.usecase.repositories

import hector.ruiz.domain.PhotoUi

interface PhotoRepository {

    suspend fun addPhoto(photo: PhotoUi)

    suspend fun getAllPhotos(size: Int): List<PhotoUi>

    suspend fun removePhoto(photo: PhotoUi)
}
