package hector.ruiz.data.repositories

import hector.ruiz.data.datasources.MemoryDataSource
import hector.ruiz.usecase.repositories.PhotoRepository
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(private val memoryDataSource: MemoryDataSource) :
    PhotoRepository {

    override suspend fun addPhoto(photoPath: String) = memoryDataSource.addPhoto(photoPath)

    override suspend fun getAllPhotos() = memoryDataSource.getAllPhotos()

    override suspend fun removePhoto(photoId: Int) = memoryDataSource.removePhoto(photoId)
}
