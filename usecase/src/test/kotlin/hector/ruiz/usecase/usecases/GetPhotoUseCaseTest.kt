package hector.ruiz.usecase.usecases

import hector.ruiz.usecase.repositories.PhotoRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK

class GetPhotoUseCaseTest {

    init {
        MockKAnnotations.init(this)
    }

    @MockK
    private lateinit var photoRepository: PhotoRepository

    private val getCharacterUseCase by lazy {
        GetPhotosUseCase(photoRepository)
    }

    /*@Test
    fun `getCharacterUseCase request getCharacter`() {
        val responseData = mockk<ResponseResult<ResponseData>>()
        coEvery { photoRepository.getPhoto(CHARACTER_ID) } returns responseData

        val result = runBlocking { getCharacterUseCase(CHARACTER_ID) }

        assertEquals(responseData, result)
    }

    private companion object {
        const val CHARACTER_ID = 2
    }*/
}
