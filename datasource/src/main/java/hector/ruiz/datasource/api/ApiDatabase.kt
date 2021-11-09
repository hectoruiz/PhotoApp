package hector.ruiz.datasource.api

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hector.ruiz.commons.entities.Photo
import hector.ruiz.datasource.converter.TimeConverter

@Database(
    entities = [Photo::class],
    version = 2
)
@TypeConverters(TimeConverter::class)
abstract class ApiDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
}
