package hector.ruiz.photoapp.di

import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import hector.ruiz.photoapp.ui.adapter.PhotoAdapter

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @Provides
    fun providerPhotoAdapter(picasso: Picasso): PhotoAdapter {
        return PhotoAdapter(picasso)
    }
}
