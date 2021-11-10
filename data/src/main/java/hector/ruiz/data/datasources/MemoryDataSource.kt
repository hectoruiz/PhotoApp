package hector.ruiz.data.datasources

import hector.ruiz.domain.PhotoUi

interface MemoryDataSource {

    suspend fun addPhoto(photo: PhotoUi): PhotoUi

    suspend fun getAllPhotos(): List<PhotoUi>

    suspend fun removePhoto(photo: PhotoUi)
}
