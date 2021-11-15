package hector.ruiz.photoapp.di

import android.content.Context
import androidx.room.Room
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
    fun providerApiDatabase(@ApplicationContext context: Context): ApiDatabase {
        return Room.databaseBuilder(context, ApiDatabase::class.java, DATABASE_NAME).build()
    }

    private const val DATABASE_NAME = "database"
}
