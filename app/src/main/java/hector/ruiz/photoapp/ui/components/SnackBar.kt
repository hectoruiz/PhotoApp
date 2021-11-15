package hector.ruiz.photoapp.ui.components

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun displaySnackBarShort(scope: CoroutineScope, scaffoldState: ScaffoldState, message: String) {
    displaySnackBar(scope, scaffoldState, message, SnackbarDuration.Short)
}

fun displaySnackBarLong(scope: CoroutineScope, scaffoldState: ScaffoldState, message: String) {
    displaySnackBar(scope, scaffoldState, message, SnackbarDuration.Long)
}

fun displaySnackBarIndefinite(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    message: String,
) {
    displaySnackBar(scope, scaffoldState, message, SnackbarDuration.Indefinite)
}

private fun displaySnackBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    message: String,
    duration: SnackbarDuration,
) {
    scope.launch {
        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
        scaffoldState.snackbarHostState.showSnackbar(
            message = message,
            duration = duration,
        )
    }
}
