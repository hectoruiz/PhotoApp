package hector.ruiz.domain

import java.time.OffsetDateTime

data class PhotoUi(
    var id: Long = 0L,
    val date: OffsetDateTime,
    val path: String
)
