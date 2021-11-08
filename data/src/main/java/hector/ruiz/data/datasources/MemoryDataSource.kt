package hector.ruiz.data.datasources

import hector.ruiz.domain.Photo

interface MemoryDataSource {

    suspend fun addPhoto(photoPath: String)

    suspend fun getAllPhotos(): List<Photo>

    suspend fun removePhoto(photoId: Int)
}
