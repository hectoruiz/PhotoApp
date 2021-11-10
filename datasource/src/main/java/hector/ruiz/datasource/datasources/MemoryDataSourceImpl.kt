package hector.ruiz.datasource.datasources

import hector.ruiz.data.datasources.MemoryDataSource
import hector.ruiz.datasource.api.ApiDatabase
import hector.ruiz.datasource.mapper.PhotoMapper
import hector.ruiz.domain.PhotoUi
import javax.inject.Inject

class MemoryDataSourceImpl @Inject constructor(
    private val apiDatabase: ApiDatabase,
    private val photoMapper: PhotoMapper
) : MemoryDataSource {

    override suspend fun addPhoto(photo: PhotoUi) =
        apiDatabase.photoDao().add(photoMapper.uiModelToModel(photo)).let { photoId ->
            PhotoUi(photoId, photo.date, photo.path)
        }

    override suspend fun getAllPhotos(): List<PhotoUi> =
        apiDatabase.photoDao().getAll().map { photo ->
            photoMapper.modelToUiModel(photo)
        }

    override suspend fun removePhoto(photo: PhotoUi) {
        apiDatabase.photoDao().delete(photoMapper.uiModelToModel(photo))
    }
}
