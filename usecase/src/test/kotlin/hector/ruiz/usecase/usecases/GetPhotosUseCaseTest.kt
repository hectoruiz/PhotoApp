package hector.ruiz.usecase.usecases

import hector.ruiz.domain.PhotoUi
import hector.ruiz.usecase.repositories.PhotoRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetPhotosUseCaseTest {

    init {
        MockKAnnotations.init(this)
    }

    @MockK
    lateinit var repository: PhotoRepository

    private val getPhotosUseCase by lazy {
        GetPhotosUseCase(repository)
    }

    @Test
    fun `getting all the photos successfully`() {
        val photoUi = mockk<PhotoUi>()
        val response = listOf(photoUi)
        coEvery { repository.getAllPhotos() } returns response

        val result = runBlocking {
            getPhotosUseCase()
        }

        Assert.assertEquals(response, result)
    }

    @Test
    fun `empty list getting all the photos`() {
        val response = emptyList<PhotoUi>()
        coEvery { repository.getAllPhotos() } returns response

        val result = runBlocking {
            getPhotosUseCase()
        }

        Assert.assertEquals(response, result)
        Assert.assertEquals(response.size, result.size)
    }
}
