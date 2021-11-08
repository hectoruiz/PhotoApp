package hector.ruiz.usecase.usecases

import hector.ruiz.usecase.repositories.PhotoRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK

class AddPhotoUseCaseTest {

    init {
        MockKAnnotations.init(this)
    }

    @MockK
    private lateinit var photoRepository: PhotoRepository

    private val getCharactersUseCase by lazy {
        AddPhotoUseCase(photoRepository)
    }

    /*@Test
    fun `getCharactersUseCase request getCharacters`() {
        val responseData = mockk<ResponseResult<ResponseData>>()
        coEvery { photoRepository.addPhoto(PAGE_NUMBER) } returns responseData

        val result = runBlocking { getCharactersUseCase(PAGE_NUMBER) }

        assertEquals(responseData, result)
    }

    private companion object {
        const val PAGE_NUMBER = 2
    }*/
}
