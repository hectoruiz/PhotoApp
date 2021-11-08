package hector.ruiz.photoapp.di

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hector.ruiz.datasource.api.ApiDatabase
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun providerApiClient(): ApiDatabase {
        return ApiDatabase()
    }

    @Provides
    @Singleton
    fun providerPicasso(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient
    ): Picasso {
        return Picasso.Builder(context).downloader(OkHttp3Downloader(okHttpClient)).build()
    }
}
