package hector.ruiz.photoapp.ui.photo.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.android.material.composethemeadapter.MdcTheme
import hector.ruiz.domain.PhotoUi
import hector.ruiz.photoapp.R
import hector.ruiz.photoapp.ui.components.CircularProgress

@ExperimentalCoilApi
@Composable
fun Item(photoUi: PhotoUi, onRemoveClick: ((PhotoUi?) -> Unit)?) {
    MdcTheme {
        val painter = rememberImagePainter(
            data = photoUi.path,
            builder = {
                placeholder(R.drawable.photo_placeholder)
                error(R.drawable.photo_error)
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = dimensionResource(id = R.dimen.photo_bottom_margin))
        ) {
            Image(
                painter = painter,
                contentDescription = stringResource(id = R.string.picture_description),
                modifier = Modifier.size(dimensionResource(id = R.dimen.photo_size))
            )

            when (painter.state) {
                is ImagePainter.State.Loading -> {
                    CircularProgress(modifier = Modifier.align(Alignment.Center))
                }
                is ImagePainter.State.Success, is ImagePainter.State.Error -> {
                    IconButton(
                        onClick = {
                            onRemoveClick?.invoke(photoUi)
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd),
                    ) {
                        Icon(painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = stringResource(id = R.string.picture_description))
                    }
                }
                else -> {
                }
            }
        }
    }
}
