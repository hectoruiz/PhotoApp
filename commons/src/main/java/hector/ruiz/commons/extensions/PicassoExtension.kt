package hector.ruiz.commons.extensions

import android.net.Uri
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import hector.ruiz.commons.R

fun Picasso.loadImage(
    fileUri: Uri?,
    appCompatImageView: AppCompatImageView?,
    circularProgressIndicator: CircularProgressIndicator?
) {
    this
        .load(fileUri)
        .fit()
        .placeholder(R.drawable.photo_placeholder)
        .error(R.drawable.photo_error)
        .into(appCompatImageView, object : Callback {
            override fun onSuccess() {
                circularProgressIndicator?.isVisible = false
            }

            override fun onError(e: Exception?) {
                circularProgressIndicator?.isVisible = false
                e?.printStackTrace()
            }
        })
}
