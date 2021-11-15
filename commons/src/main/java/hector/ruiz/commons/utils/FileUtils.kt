package hector.ruiz.commons.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import hector.ruiz.commons.utils.Constants.IMG_EXTENSION
import hector.ruiz.commons.utils.Constants.PREFIX_IMG_NAME
import hector.ruiz.commons.utils.Constants.PROVIDER
import hector.ruiz.commons.utils.Constants.SEPARATOR_IMG_NAME
import java.io.File
import java.time.OffsetDateTime

object FileUtils {
    fun getNewFileUri(context: Context, fileName: String, appId: String): Uri? {
        val imagePath = File(context.filesDir, ".")
        val newFile = File(imagePath, fileName)
        return FileProvider.getUriForFile(context, "$appId.$PROVIDER", newFile)
    }

    fun getNewFileName(now: OffsetDateTime) =
        "$PREFIX_IMG_NAME$SEPARATOR_IMG_NAME$now.year$now.monthValue$now.dayOfMonth" +
                "$SEPARATOR_IMG_NAME$now.hour$now.minute$now.second$IMG_EXTENSION"
}
