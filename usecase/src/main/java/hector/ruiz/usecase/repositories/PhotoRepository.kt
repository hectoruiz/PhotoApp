package hector.ruiz.usecase.repositories

import hector.ruiz.domain.PhotoUi

interface PhotoRepository {

    suspend fun addPhoto(photo: PhotoUi): PhotoUi

    suspend fun getAllPhotos(): List<PhotoUi>

    suspend fun removePhoto(photo: PhotoUi)
}
