package hector.ruiz.photoapp.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.google.android.material.composethemeadapter.MdcTheme
import hector.ruiz.photoapp.R

@Composable
fun CircularProgress(modifier: Modifier) {
    MdcTheme {
        CircularProgressIndicator(
            modifier = modifier
                .size(dimensionResource(id = R.dimen.progress_size)))
    }
}
