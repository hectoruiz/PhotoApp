package hector.ruiz.usecase.usecases

import hector.ruiz.domain.PhotoUi
import hector.ruiz.usecase.repositories.PhotoRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class AddPhotoUseCaseTest {

    init {
        MockKAnnotations.init(this)
    }

    @MockK
    lateinit var repository: PhotoRepository

    private val addPhotoUseCase by lazy {
        AddPhotoUseCase(repository)
    }

    @Test
    fun `adding a photo successfully`() {
        val param = mockk<PhotoUi>()
        val response = mockk<PhotoUi>()
        coEvery { repository.addPhoto(param) } returns response

        val result = runBlocking {
            addPhotoUseCase(param)
        }

        assertEquals(response, result)
    }
}
