package hector.ruiz.photoapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.res.stringResource
import coil.annotation.ExperimentalCoilApi
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import hector.ruiz.photoapp.R
import hector.ruiz.photoapp.ui.photo.screen.PhotoList
import hector.ruiz.presentation.PhotoViewModel

@ExperimentalCoilApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val photoViewModel: PhotoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MdcTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(title =
                        { Text(text = stringResource(id = R.string.app_name)) })
                    },
                    content = { PhotoList(photoViewModel) }
                )
            }
        }
    }
}
