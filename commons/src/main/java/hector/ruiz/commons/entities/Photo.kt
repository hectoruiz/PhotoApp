package hector.ruiz.commons.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "photo")
data class Photo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "photoId")
    val id: Int,
    @ColumnInfo(name = "photoDateCreated")
    val date: OffsetDateTime,
    @ColumnInfo(name = "photoPath")
    val path: String
)
