package hector.ruiz.data.datasources

import hector.ruiz.domain.PhotoUi

interface MemoryDataSource {

    suspend fun addPhoto(photo: PhotoUi)

    suspend fun getAllPhotos(size: Int): List<PhotoUi>

    suspend fun removePhoto(photo: PhotoUi)
}
