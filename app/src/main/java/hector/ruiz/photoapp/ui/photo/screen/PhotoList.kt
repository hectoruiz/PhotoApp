package hector.ruiz.photoapp.ui.photo.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import coil.annotation.ExperimentalCoilApi
import com.google.android.material.composethemeadapter.MdcTheme
import hector.ruiz.commons.utils.FileUtils.getNewFileName
import hector.ruiz.commons.utils.FileUtils.getNewFileUri
import hector.ruiz.commons.utils.ResultRequest
import hector.ruiz.domain.PhotoUi
import hector.ruiz.photoapp.BuildConfig
import hector.ruiz.photoapp.R
import hector.ruiz.photoapp.ui.components.CircularProgress
import hector.ruiz.photoapp.ui.components.displaySnackBarIndefinite
import hector.ruiz.photoapp.ui.components.displaySnackBarLong
import hector.ruiz.photoapp.ui.components.displaySnackBarShort
import hector.ruiz.photoapp.ui.photo.screen.Constants.CELLS_NUMBER
import hector.ruiz.presentation.PhotoViewModel
import java.time.OffsetDateTime

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun PhotoList(
    photoViewModel: PhotoViewModel,
) {
    val context = LocalContext.current
    var photoUi: PhotoUi? = null
    val takePhotoResult =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                photoViewModel.addPhotoToDatabase(photoUi)
            }
        }
    photoViewModel.getAllPhoto()

    MdcTheme {
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        Scaffold(
            scaffoldState = scaffoldState,
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        val now = OffsetDateTime.now()
                        try {
                            getNewFileUri(context,
                                getNewFileName(now),
                                BuildConfig.APPLICATION_ID)?.let { uri ->
                                photoUi = PhotoUi(date = now, path = uri.toString())
                                takePhotoResult.launch(uri)
                            }
                        } catch (e: IllegalArgumentException) {
                            displaySnackBarIndefinite(scope,
                                scaffoldState,
                                context.getString(R.string.error_uri_file))
                        }
                    },
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.big_margin_parent)))
                {
                    Icon(imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.fab_description))
                }
            }) {
            Box(modifier = Modifier.fillMaxSize()) {
                val photoList =
                    photoViewModel.photoData.observeAsState().value ?: emptyList()
                LazyVerticalGrid(cells = GridCells.Fixed(CELLS_NUMBER),
                    contentPadding = PaddingValues(
                        dimensionResource(id = R.dimen.small_margin_parent)), content = {
                        items(photoList.size) { index ->
                            Item(photoList[index]) { photoViewModel.removePhotoFromDatabase(it) }
                        }
                    })
                if (photoViewModel.isLoading.observeAsState(true).value) {
                    CircularProgress(modifier = Modifier.align(Alignment.Center))
                }
            }
            val resultRequest: ResultRequest? =
                photoViewModel.resultRequest.observeAsState().value
            val resultMessage = when (resultRequest) {
                is ResultRequest.Success.Add -> R.string.insert_success
                is ResultRequest.Success.Remove -> R.string.remove_success
                is ResultRequest.Error.Get -> R.string.recover_error
                is ResultRequest.Error.Add -> R.string.insert_error
                is ResultRequest.Error.Remove -> R.string.remove_error
                is ResultRequest.Error.Request -> R.string.request_error
                else -> null
            }?.let {
                stringResource(it)
            }
            if (resultMessage != null) {
                if (resultRequest is ResultRequest.Success) {
                    displaySnackBarShort(
                        scope = scope, scaffoldState = scaffoldState,
                        message = resultMessage)
                } else if (resultRequest is ResultRequest.Error) {
                    displaySnackBarLong(
                        scope = scope, scaffoldState = scaffoldState,
                        message = resultMessage)
                }
            }
        }
    }
}

object Constants {
    const val CELLS_NUMBER = 3
}

