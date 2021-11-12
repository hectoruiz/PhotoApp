package hector.ruiz.commons.extensions

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar

fun Fragment.snackBar(stringId: Int, duration: Int) {
    view?.let {
        Snackbar.make(it, stringId, duration).show()
    }
}

fun Fragment.snackBarIndefinite(stringId: Int) = snackBar(stringId, LENGTH_INDEFINITE)

fun Fragment.snackBarLong(stringId: Int) = snackBar(stringId, LENGTH_LONG)
