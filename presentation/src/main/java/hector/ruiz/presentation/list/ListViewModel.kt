package hector.ruiz.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import hector.ruiz.domain.PhotoUi
import hector.ruiz.usecase.usecases.GetPhotosUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val getPhotosUseCase: GetPhotosUseCase) :
    ViewModel() {

    val photoList: Flow<PagingData<PhotoUi>> = getPaginatedList()

    private fun getPaginatedList(): Flow<PagingData<PhotoUi>> {
        return Pager(
            config = PagingConfig(
                pageSize = 80,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PagingDataSourceImpl(getPhotosUseCase)
            }
        ).flow.cachedIn(viewModelScope)
    }
}
