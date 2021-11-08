package hector.ruiz.datasource.datasources

import hector.ruiz.data.datasources.MemoryDataSource
import hector.ruiz.domain.Photo
import javax.inject.Inject

class MemoryDataSourceImpl @Inject constructor() : MemoryDataSource {

    override suspend fun addPhoto(photoPath: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllPhotos(): List<Photo> {
        TODO("Not yet implemented")
    }

    override suspend fun removePhoto(photoId: Int) {
        TODO("Not yet implemented")
    }
}
