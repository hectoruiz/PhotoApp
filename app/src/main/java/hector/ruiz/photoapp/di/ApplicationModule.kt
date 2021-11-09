package hector.ruiz.photoapp.di

import android.content.Context
import androidx.room.Room
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hector.ruiz.datasource.api.ApiDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun providerApiClient(@ApplicationContext context: Context): ApiDatabase {
        return Room.databaseBuilder(context, ApiDatabase::class.java, DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    fun providerPicasso(@ApplicationContext context: Context): Picasso {
        return Picasso.Builder(context).build()
    }

    private const val DATABASE_NAME = "database"
}
