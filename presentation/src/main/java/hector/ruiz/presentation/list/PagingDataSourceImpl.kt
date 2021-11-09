package hector.ruiz.presentation.list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import hector.ruiz.domain.PhotoUi
import hector.ruiz.usecase.usecases.GetPhotosUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PagingDataSourceImpl @Inject constructor(private val getPhotosUseCase: GetPhotosUseCase) :
    PagingSource<Int, PhotoUi>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoUi> {
        return withContext(Dispatchers.IO) {
            val size = params.key ?: DEFAULT_SIZE
            try {
                getPhotosUseCase(size).let { photoList ->
                    LoadResult.Page(
                        data = photoList,
                        prevKey = null,
                        nextKey = if (photoList.isNullOrEmpty()) null else size + DEFAULT_SIZE
                    )
                }
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PhotoUi>): Int? {
        return state.anchorPosition
    }

    private companion object {
        const val DEFAULT_SIZE = 80
    }
}
