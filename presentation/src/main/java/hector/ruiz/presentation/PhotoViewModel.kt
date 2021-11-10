package hector.ruiz.presentation

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import hector.ruiz.commons.utils.CollectionUtils.listToMutableList
import hector.ruiz.commons.utils.CollectionUtils.mutableListToList
import hector.ruiz.commons.utils.CrudOperations
import hector.ruiz.domain.PhotoUi
import hector.ruiz.usecase.usecases.AddPhotoUseCase
import hector.ruiz.usecase.usecases.GetPhotosUseCase
import hector.ruiz.usecase.usecases.RemovePhotoUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase,
    private val addPhotoUseCase: AddPhotoUseCase,
    private val removePhotoUseCase: RemovePhotoUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _errorRequest.postValue(null)
    }

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _photoData: MediatorLiveData<Pair<List<PhotoUi>, CrudOperations>> =
        MediatorLiveData()
    val photoData: MediatorLiveData<Pair<List<PhotoUi>, CrudOperations>>
        get() = _photoData

    private val _errorRequest: MutableLiveData<CrudOperations> = MutableLiveData()
    val errorRequest: LiveData<CrudOperations>
        get() = _errorRequest

    fun addPhotoToDatabase(photoUi: PhotoUi?) = viewModelScope.launch(exceptionHandler) {
        _isLoading.postValue(true)
        photoUi?.let { photoUi ->
            addPhotoUseCase(photoUi).let { photoUiAdded ->
                val tempList: MutableLiveData<MutableList<PhotoUi>> = MutableLiveData()
                tempList.value = listToMutableList(_photoData.value?.first).also {
                    it?.add(photoUiAdded)
                }
                _photoData.postValue(Pair(mutableListToList(tempList.value), CrudOperations.ADD))
                _isLoading.postValue(false)
            }
        } ?: _errorRequest.postValue(CrudOperations.ADD)
    }

    fun getAllPhoto() = viewModelScope.launch(exceptionHandler) {
        _isLoading.postValue(true)
        val result = getPhotosUseCase()
        if (result.isEmpty()) _errorRequest.postValue(CrudOperations.GET)
        else _photoData.postValue(Pair(result, CrudOperations.GET))
        _isLoading.postValue(false)
    }

    fun removePhotoToDatabase(photoUi: PhotoUi?) = viewModelScope.launch(exceptionHandler) {
        _isLoading.postValue(true)
        photoUi?.let { photoUi ->
            removePhotoUseCase(photoUi).let {
                val tempList: MutableLiveData<MutableList<PhotoUi>> = MutableLiveData()
                tempList.value = listToMutableList(_photoData.value?.first).also {
                    it?.remove(photoUi)
                }
                _photoData.postValue(Pair(mutableListToList(tempList.value), CrudOperations.REMOVE))
                _isLoading.postValue(false)
            }
        } ?: _errorRequest.postValue(CrudOperations.REMOVE)
    }
}
