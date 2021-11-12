package hector.ruiz.datasource.converter

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.OffsetDateTime
import java.time.ZoneOffset

class TimeConverterTest {

    @Test
    fun toOffsetDateTime() {
        assertEquals(offsetDateTime, TimeConverter.toOffsetDateTime(STRING_DATE))
    }

    @Test
    fun fromOffsetDateTime() {
        assertEquals(STRING_DATE, TimeConverter.fromOffsetDateTime(offsetDateTime))
    }

    private companion object {
        val offsetDateTime: OffsetDateTime = OffsetDateTime.of(
            2000, 2,
            22, 2, 2, 22, 2222, ZoneOffset.UTC
        )
        const val STRING_DATE = "2000-02-22T02:02:22.000002222Z"
    }
}
