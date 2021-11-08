package hector.ruiz.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import hector.ruiz.domain.Photo
import hector.ruiz.usecase.usecases.GetPhotosUseCase
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val getPhotosUseCase: GetPhotosUseCase) :
    ViewModel() {

    val photoList: LiveData<PagingData<Photo>> = getPaginatedList()

    private fun getPaginatedList(): LiveData<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 80,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PagingDataSourceImpl(getPhotosUseCase)
            }
        ).liveData.cachedIn(viewModelScope)
    }
}
