package hector.ruiz.data.repositories

import hector.ruiz.data.datasources.MemoryDataSource
import hector.ruiz.domain.PhotoUi
import hector.ruiz.usecase.repositories.PhotoRepository
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(private val memoryDataSource: MemoryDataSource) :
    PhotoRepository {

    override suspend fun addPhoto(photo: PhotoUi) = memoryDataSource.addPhoto(photo)

    override suspend fun getAllPhotos() = memoryDataSource.getAllPhotos()

    override suspend fun removePhoto(photo: PhotoUi) = memoryDataSource.removePhoto(photo)
}
