package hector.ruiz.photoapp.di

import androidx.paging.PagingSource
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import hector.ruiz.domain.PhotoUi
import hector.ruiz.photoapp.ui.PhotoAdapter
import hector.ruiz.presentation.list.PagingDataSourceImpl
import hector.ruiz.usecase.usecases.GetPhotosUseCase

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @Provides
    fun providerPhotoAdapter(picasso: Picasso): PhotoAdapter {
        return PhotoAdapter(picasso)
    }

    @Provides
    fun providerPagingDataSourceImpl(getPhotosUseCase: GetPhotosUseCase): PagingSource<Int, PhotoUi> {
        return PagingDataSourceImpl(getPhotosUseCase)
    }
}
