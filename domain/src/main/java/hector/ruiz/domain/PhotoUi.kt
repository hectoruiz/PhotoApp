package hector.ruiz.domain

import java.time.OffsetDateTime

data class PhotoUi(
    var id: Int = 0,
    val date: OffsetDateTime,
    val path: String
)
