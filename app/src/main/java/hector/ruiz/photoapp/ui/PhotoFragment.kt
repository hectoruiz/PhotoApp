package hector.ruiz.photoapp.ui

import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider.getUriForFile
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import hector.ruiz.commons.extensions.loadImage
import hector.ruiz.commons.extensions.snackBarIndefinite
import hector.ruiz.domain.PhotoUi
import hector.ruiz.photoapp.BuildConfig
import hector.ruiz.photoapp.R
import hector.ruiz.photoapp.databinding.FragmentPhotoBinding
import java.io.File
import java.time.OffsetDateTime
import javax.inject.Inject

@AndroidEntryPoint
class PhotoFragment : Fragment() {

    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding
    private var photoUi: PhotoUi? = null

    @Inject
    lateinit var photoAdapter: PhotoAdapter

    @Inject
    lateinit var picasso: Picasso

    private val takePhotoResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                getImageUri()?.let { fileUri ->
                    picasso.loadImage(
                        fileUri,
                        binding?.testImage,
                        binding?.photoListProgress
                    )
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoBinding.inflate(inflater, container, false)
        initRecyclerView()

        return binding?.root
    }

    private fun initRecyclerView() {
        binding?.photoList?.adapter =
            photoAdapter.also { it.withLoadStateFooter(PhotoLoadStateAdapter { it.retry() }) }
        photoAdapter.onDeleteClick = { photo ->
            photo?.let {
                // TODO
                // Call view model to remove the photo id selected
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO
        // Call view model to get all the photos

        binding?.fab?.setOnClickListener {
            takePhoto()
            // TODO
            // Manage camera and storage permissions
            // Call view model to add the photo id selected
        }
    }

    private fun takePhoto() {
        val now = OffsetDateTime.now()
        getNewFileUri(getNewFileName(now))?.let { uri ->
            photoUi = PhotoUi(date = now, path = uri.toString())
            takePhotoResult.launch(uri)
        }
    }

    private fun getNewFileUri(fileName: String): Uri? {
        return context?.let {
            try {
                val imagePath = File(it.filesDir, ".")
                val newFile = File(imagePath, fileName)
                getUriForFile(it, BuildConfig.APPLICATION_ID + "." + PROVIDER, newFile)
            } catch (e: IllegalArgumentException) {
                snackBarIndefinite(R.string.error_uri_file)
                null
            }
        }
    }

    private fun getNewFileName(now: OffsetDateTime): String {
        return PREFIX_IMG_NAME + SEPARATOR_IMG_NAME + now.year + now.monthValue + now.dayOfMonth +
                SEPARATOR_IMG_NAME + now.hour + now.minute + now.second + IMG_EXTENSION
    }

    private fun getImageUri(): Uri? {
        return Uri.parse(photoUi?.path).let { uri ->
            context?.contentResolver?.query(
                uri, null, null,
                null, null
            )?.let { cursor ->
                val nameFileIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                cursor.moveToFirst()
                val imageUri = File(context?.filesDir, cursor.getString(nameFileIndex)).toUri()
                cursor.close()
                imageUri
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = PhotoFragment()
        private const val PROVIDER = "provider"
        private const val PREFIX_IMG_NAME = "IMG"
        private const val SEPARATOR_IMG_NAME = "_"
        private const val IMG_EXTENSION = ".jpg"
    }
}
