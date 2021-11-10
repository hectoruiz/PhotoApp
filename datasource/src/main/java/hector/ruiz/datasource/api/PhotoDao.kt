package hector.ruiz.datasource.api

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import hector.ruiz.commons.entities.Photo

@Dao
interface PhotoDao {

    @Query("SELECT * FROM Photo WHERE photoId")
    suspend fun getAll(): List<Photo>

    @Delete
    suspend fun delete(photo: Photo)

    @Insert
    suspend fun add(photo: Photo): Long
}
