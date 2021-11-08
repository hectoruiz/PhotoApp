package hector.ruiz.presentation.list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import hector.ruiz.domain.Photo
import hector.ruiz.usecase.usecases.GetPhotosUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PagingDataSourceImpl @Inject constructor(private val getPhotosUseCase: GetPhotosUseCase) :
    PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return withContext(Dispatchers.IO) {
            try {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
                /*
                val photoId = params.key ?: DEFAULT_PAGE
                getPhotosUseCase(nextPageNumber).let { responseResult ->
                    val responseList: List<Photo>? =
                        responseResult.data?.charactersData?.characterList?.mapNotNull {
                            it
                        }
                    LoadResult.Page(
                        data = responseList ?: emptyList(),
                        prevKey = null,
                        nextKey = if (responseList.isNullOrEmpty()) {
                            null
                        } else {
                            responseResult.data?.charactersData?.run {
                                offset?.plus(limit ?: DEFAULT_PAGE_SIZE)
                            }
                        }
                    )
                }*/
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition
    }

    private companion object {
        const val DEFAULT_PAGE = 0
        const val DEFAULT_PAGE_SIZE = 20
    }
}
