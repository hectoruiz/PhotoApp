package hector.ruiz.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import hector.ruiz.commons.utils.CrudOperations
import hector.ruiz.domain.PhotoUi
import hector.ruiz.presentation.utils.CoroutineRule
import hector.ruiz.usecase.usecases.AddPhotoUseCase
import hector.ruiz.usecase.usecases.GetPhotosUseCase
import hector.ruiz.usecase.usecases.RemovePhotoUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any

@ExperimentalCoroutinesApi
class PhotoViewModelTest {

    init {
        MockKAnnotations.init(this)
    }

    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var getPhotosUseCase: GetPhotosUseCase

    @MockK
    lateinit var addPhotoUseCase: AddPhotoUseCase

    @MockK
    lateinit var removePhotoUseCase: RemovePhotoUseCase

    private val photoViewModel by lazy {
        PhotoViewModel(getPhotosUseCase, addPhotoUseCase, removePhotoUseCase)
    }

    @Test
    fun `addPhotoToDatabase with null photo added`() {
        val photoUi: PhotoUi? = null

        runBlocking {
            photoViewModel.addPhotoToDatabase(photoUi)
        }

        with(photoViewModel) {
            assertNull(this.photoData.value?.first)
            assertNull(this.photoData.value?.second)
            assertEquals(CrudOperations.ADD, this.errorRequest.value)
            assertEquals(false, this.isLoading.value)
        }
    }

    @Test
    fun `addPhotoToDatabase with correct photo added`() {
        val parameter = mockk<PhotoUi>()
        val responseData = mockk<PhotoUi>()

        coEvery { addPhotoUseCase(parameter) } returns responseData

        runBlocking {
            photoViewModel.addPhotoToDatabase(parameter)
        }

        with(photoViewModel) {
            assertEquals(listOf(responseData), this.photoData.value?.first)
            assertEquals(CrudOperations.ADD, this.photoData.value?.second)
            assertNull(this.errorRequest.value)
            assertEquals(false, this.isLoading.value)
        }
    }

    @Test
    fun `getAllPhoto response with non empty list of photos`() {
        val responseData = mockk<List<PhotoUi>>()

        coEvery { getPhotosUseCase() } returns responseData
        every { responseData.isEmpty() } returns false
        runBlocking {
            photoViewModel.getAllPhoto()
        }

        with(photoViewModel) {
            assertEquals(responseData, this.photoData.value?.first)
            assertEquals(CrudOperations.GET, this.photoData.value?.second)
            assertNull(this.errorRequest.value)
            assertEquals(false, this.isLoading.value)
        }
    }

    @Test
    fun `getAllPhoto response with empty list of photos`() {
        val responseData = mockk<List<PhotoUi>>()

        coEvery { getPhotosUseCase() } returns responseData
        every { responseData.isEmpty() } returns true
        runBlocking {
            photoViewModel.getAllPhoto()
        }

        with(photoViewModel) {
            assertNull(this.photoData.value?.first)
            assertNull(this.photoData.value?.second)
            assertEquals(CrudOperations.GET, this.errorRequest.value)
            assertEquals(false, this.isLoading.value)
        }
    }

    @Test
    fun `removePhotoFromDatabase with null photo to remove`() {
        val photoUi: PhotoUi? = null

        runBlocking {
            photoViewModel.removePhotoFromDatabase(photoUi)
        }

        with(photoViewModel) {
            assertNull(this.photoData.value?.first)
            assertNull(this.photoData.value?.second)
            assertEquals(CrudOperations.REMOVE, this.errorRequest.value)
            assertEquals(false, this.isLoading.value)
        }
    }

    @Test
    fun `removePhotoFromDatabase with correct photo to remove`() {
        val parameter = mockk<PhotoUi>()
        val photoUi = mockk<PhotoUi>()

        coEvery { removePhotoUseCase(parameter) } returns any()

        photoViewModel.photoData.postValue(Pair(listOf(parameter, photoUi), CrudOperations.REMOVE))

        runBlocking {
            photoViewModel.removePhotoFromDatabase(parameter)
        }

        with(photoViewModel) {
            assertThrows(NoSuchElementException::class.java) {
                this.photoData.value?.first?.first {
                    it == parameter
                }
            }
            assertEquals(false, this.photoData.value?.first?.isEmpty())
            assertEquals(CrudOperations.REMOVE, this.photoData.value?.second)
            assertNull(this.errorRequest.value)
            assertEquals(false, this.isLoading.value)
        }
    }
}
