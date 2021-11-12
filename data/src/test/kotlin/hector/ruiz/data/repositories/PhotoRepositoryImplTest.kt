package hector.ruiz.data.repositories

import hector.ruiz.data.datasources.MemoryDataSource
import hector.ruiz.domain.PhotoUi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class PhotoRepositoryImplTest {

    init {
        MockKAnnotations.init(this)
    }

    @MockK
    lateinit var memoryDataSource: MemoryDataSource

    private val photoRepositoryImpl by lazy {
        PhotoRepositoryImpl(memoryDataSource)
    }

    @Test
    fun `adding a photo successfully`() {
        val param = mockk<PhotoUi>(relaxed = true)
        val response = mockk<PhotoUi>(relaxed = true)
        coEvery { memoryDataSource.addPhoto(param) } returns response

        val result = runBlocking {
            photoRepositoryImpl.addPhoto(param)
        }

        assertEquals(response, result)
    }

    @Test
    fun `getting all the photos successfully`() {
        val photoUi = mockk<PhotoUi>()
        val response = listOf(photoUi)
        coEvery { memoryDataSource.getAllPhotos() } returns response

        val result = runBlocking {
            photoRepositoryImpl.getAllPhotos()
        }

        assertEquals(response, result)
    }

    @Test
    fun `empty list getting all the photos`() {
        val response = emptyList<PhotoUi>()
        coEvery { memoryDataSource.getAllPhotos() } returns response

        val result = runBlocking {
            photoRepositoryImpl.getAllPhotos()
        }

        assertEquals(response, result)
        assertEquals(response.size, result.size)
    }

    @Test
    fun `removing a photo successfully`() {
        val photoUi = mockk<PhotoUi>()
        coEvery { memoryDataSource.removePhoto(photoUi) } returns mockk()

        runBlocking {
            photoRepositoryImpl.removePhoto(photoUi)
        }

        coVerify {
            memoryDataSource.removePhoto(photoUi)
            photoRepositoryImpl.removePhoto(photoUi)
        }
    }
}
