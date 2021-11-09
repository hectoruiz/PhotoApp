package hector.ruiz.datasource.api

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import hector.ruiz.commons.entities.Photo

@Dao
interface PhotoDao {

    @Query(
        """SELECT * FROM Photo WHERE photoId IN (SELECT photoId FROM Photo 
        ORDER BY datetime(photoDateCreated) ASC LIMIT :size)"""
    )
    suspend fun getAll(size: Int): List<Photo>

    @Delete
    suspend fun delete(photo: Photo)

    @Insert
    suspend fun add(photo: Photo)
}
