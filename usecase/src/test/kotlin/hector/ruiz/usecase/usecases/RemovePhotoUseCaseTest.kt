package hector.ruiz.usecase.usecases

import hector.ruiz.domain.PhotoUi
import hector.ruiz.usecase.repositories.PhotoRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RemovePhotoUseCaseTest {

    init {
        MockKAnnotations.init(this)
    }

    @MockK
    lateinit var repository: PhotoRepository

    private val removePhotoUseCase by lazy {
        RemovePhotoUseCase(repository)
    }

    @Test
    fun `removing a photo successfully`() {
        val photoUi = mockk<PhotoUi>()
        coEvery { repository.removePhoto(photoUi) } returns mockk()

        runBlocking {
            removePhotoUseCase(photoUi)
        }

        coVerify {
            repository.removePhoto(photoUi)
            removePhotoUseCase(photoUi)
        }
    }
}
