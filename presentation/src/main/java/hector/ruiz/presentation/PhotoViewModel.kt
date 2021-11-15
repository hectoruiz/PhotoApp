package hector.ruiz.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hector.ruiz.commons.utils.CollectionUtils.listToMutableList
import hector.ruiz.commons.utils.CollectionUtils.mutableListToList
import hector.ruiz.commons.utils.ResultRequest
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
    private val removePhotoUseCase: RemovePhotoUseCase,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _isLoading.postValue(false)
        _resultRequest.postValue(ResultRequest.Error.Request)
    }

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _photoData: MutableLiveData<List<PhotoUi>> = MutableLiveData()
    val photoData: LiveData<List<PhotoUi>>
        get() = _photoData

    private val _resultRequest: MutableLiveData<ResultRequest> = MutableLiveData()
    val resultRequest: LiveData<ResultRequest>
        get() = _resultRequest

    private fun initRequest() {
        _isLoading.postValue(true)
        _resultRequest.postValue(null)
    }

    fun addPhotoToDatabase(photoUi: PhotoUi?) = viewModelScope.launch(exceptionHandler) {
        initRequest()
        photoUi?.let { photoUi ->
            addPhotoUseCase(photoUi).let { photoUiAdded ->
                val tempList: MutableLiveData<MutableList<PhotoUi>> = MutableLiveData()
                tempList.value = listToMutableList(_photoData.value ?: emptyList()).also {
                    it?.add(photoUiAdded)
                }
                _photoData.postValue(mutableListToList(tempList.value))
                _isLoading.postValue(false)
                _resultRequest.postValue(ResultRequest.Success.Add)
            }
        } ?: run {
            _resultRequest.postValue(ResultRequest.Error.Add)
            _isLoading.postValue(false)
        }
    }

    fun getAllPhoto() = viewModelScope.launch(exceptionHandler) {
        initRequest()
        val result = getPhotosUseCase()
        if (result.isEmpty()) _resultRequest.postValue(ResultRequest.Error.Get)
        else {
            _photoData.postValue(result)
            _resultRequest.postValue(null)
        }
        _isLoading.postValue(false)
    }

    fun removePhotoFromDatabase(photoUi: PhotoUi?) = viewModelScope.launch(exceptionHandler) {
        initRequest()
        photoUi?.let { photoUi ->
            removePhotoUseCase(photoUi).let {
                val tempList: MutableLiveData<MutableList<PhotoUi>> = MutableLiveData()
                tempList.value = listToMutableList(_photoData.value ?: emptyList()).also {
                    it?.remove(photoUi)
                }
                _photoData.postValue(mutableListToList(tempList.value))
                _resultRequest.postValue(ResultRequest.Success.Remove)
                _isLoading.postValue(false)
            }
        } ?: run {
            _resultRequest.postValue(ResultRequest.Error.Remove)
            _isLoading.postValue(false)
        }
    }
}
