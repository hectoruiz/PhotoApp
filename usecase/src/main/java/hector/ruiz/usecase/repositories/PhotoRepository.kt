package hector.ruiz.usecase.repositories

import hector.ruiz.domain.Photo

interface PhotoRepository {

    suspend fun addPhoto(photoPath: String)

    suspend fun getAllPhotos(): List<Photo>

    suspend fun removePhoto(photoId: Int)
}
