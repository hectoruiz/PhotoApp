package hector.ruiz.photoapp.extensions

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar

fun Fragment.snackBar(stringId: Int, duration: Int) {
    val view = view
    view?.let {
        Snackbar.make(view, stringId, duration).show()
    }
}

fun Fragment.snackBarIndefinite(stringId: Int) = snackBar(stringId, LENGTH_INDEFINITE)

fun Fragment.snackBarLong(stringId: Int) = snackBar(stringId, LENGTH_LONG)
