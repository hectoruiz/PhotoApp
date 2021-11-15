package hector.ruiz.commons.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.time.OffsetDateTime

@RunWith(AndroidJUnit4::class)
class FileUtilsTest {

    @Test
    fun getNewFileUri() {
        val context = InstrumentationRegistry.getInstrumentation().context
        assertEquals("content://hector.ruiz.photoapp.provider/images/fileName",
            FileUtils.getNewFileUri(context, "fileName", "hector.ruiz.photoapp").toString())
    }

    @Test
    fun checkNewFileName() {
        val date = OffsetDateTime.now()
        val actualDate =
            "${Constants.PREFIX_IMG_NAME}${Constants.SEPARATOR_IMG_NAME}$date.year$date.monthValue" +
                    "$date.dayOfMonth${Constants.SEPARATOR_IMG_NAME}$date.hour$date.minute" +
                    "$date.second${Constants.IMG_EXTENSION}"
        assertEquals(actualDate, FileUtils.getNewFileName(date))
    }
}
