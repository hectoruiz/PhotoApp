package hector.ruiz.datasource.datasources

import hector.ruiz.commons.entities.Photo
import hector.ruiz.datasource.api.ApiDatabase
import hector.ruiz.datasource.mapper.PhotoMapper
import hector.ruiz.domain.PhotoUi
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class MemoryDataSourceImplTest {

    init {
        MockKAnnotations.init(this)
    }

    @MockK
    lateinit var apiDatabase: ApiDatabase

    @MockK
    lateinit var photoMapper: PhotoMapper

    private val memoryDataSourceImpl by lazy {
        MemoryDataSourceImpl(apiDatabase, photoMapper)
    }

    @Test
    fun `adding a photo successfully`() {
        val photoUi = mockk<PhotoUi>(relaxed = true)
        val photo = mockk<Photo>(relaxed = true)
        coEvery { apiDatabase.photoDao().add(photo) } returns 12L
        every { photoMapper.uiModelToModel(photoUi) } returns photo

        val result = runBlocking {
            memoryDataSourceImpl.addPhoto(photoUi)
        }

        coVerify {
            photoMapper.uiModelToModel(photoUi)
            apiDatabase.photoDao().add(photo)
        }

        assertNotEquals(12L, photoUi.id)
        assertEquals(12L, result.id)
    }

    @Test
    fun `getting all the photos successfully`() {
        val photoUi = mockk<PhotoUi>()
        val photo = mockk<Photo>()
        val responseData = listOf(photo)
        coEvery { apiDatabase.photoDao().getAll() } returns responseData
        every { photoMapper.modelToUiModel(photo) } returns photoUi

        val result = runBlocking {
            memoryDataSourceImpl.getAllPhotos()
        }

        coVerifySequence {
            apiDatabase.photoDao().getAll()
            photoMapper.modelToUiModel(photo)
        }

        assertEquals(photoMapper.modelToUiModel(responseData.first()), result.first())
    }

    @Test
    fun `empty list getting all the photos`() {
        val response = emptyList<Photo>()
        coEvery { apiDatabase.photoDao().getAll() } returns response

        val result = runBlocking {
            memoryDataSourceImpl.getAllPhotos()
        }

        assertEquals(response, result)
        assertEquals(response.size, result.size)
    }

    @Test
    fun `removing a photo successfully`() {
        val photoUi = mockk<PhotoUi>()
        val photo = mockk<Photo>()
        coEvery { apiDatabase.photoDao().delete(photo) } returns mockk()
        every { photoMapper.uiModelToModel(photoUi) } returns photo

        runBlocking {
            memoryDataSourceImpl.removePhoto(photoUi)
        }

        coVerify {
            photoMapper.uiModelToModel(photoUi)
            apiDatabase.photoDao().delete(photo)
        }
    }
}
