package hector.ruiz.photoapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hector.ruiz.data.datasources.MemoryDataSource
import hector.ruiz.data.repositories.PhotoRepositoryImpl
import hector.ruiz.datasource.datasources.MemoryDataSourceImpl
import hector.ruiz.usecase.repositories.PhotoRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class MemoryModule {

    @Binds
    abstract fun bindsMemoryDataSource(memoryDataSourceImpl: MemoryDataSourceImpl): MemoryDataSource

    @Binds
    abstract fun bindsPhotoRepository(repositoryImpl: PhotoRepositoryImpl): PhotoRepository
}
