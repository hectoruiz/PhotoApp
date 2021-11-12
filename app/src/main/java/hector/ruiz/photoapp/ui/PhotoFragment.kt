package hector.ruiz.photoapp.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider.getUriForFile
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import hector.ruiz.commons.extensions.snackBarIndefinite
import hector.ruiz.commons.extensions.snackBarLong
import hector.ruiz.commons.utils.CrudOperations
import hector.ruiz.domain.PhotoUi
import hector.ruiz.photoapp.BuildConfig
import hector.ruiz.photoapp.R
import hector.ruiz.photoapp.databinding.FragmentPhotoBinding
import hector.ruiz.photoapp.ui.adapter.PhotoAdapter
import hector.ruiz.photoapp.ui.adapter.PhotoItemDecoration
import hector.ruiz.presentation.PhotoViewModel
import java.io.File
import java.time.OffsetDateTime
import javax.inject.Inject

@AndroidEntryPoint
class PhotoFragment : Fragment() {

    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding
    private var photoUi: PhotoUi? = null
    private val photoViewModel: PhotoViewModel by viewModels()
    private var removePosition: Int = 0

    @Inject
    lateinit var photoAdapter: PhotoAdapter

    private val takePhotoResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                photoViewModel.addPhotoToDatabase(photoUi)
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
        binding?.photoList?.apply {
            this.layoutManager = GridLayoutManager(
                context,
                COLUMNS_NUMBER,
                RecyclerView.VERTICAL,
                false
            )
            this.addItemDecoration(
                PhotoItemDecoration(
                    context?.resources?.getDimensionPixelSize(
                        R.dimen.small_margin_parent
                    ) ?: DEFAULT_ITEM_OFFSET
                )
            )
        }
        binding?.photoList?.adapter = photoAdapter
        photoAdapter.onDeleteClick = { photo, pos ->
            photo?.let {
                removePosition = pos
                photoViewModel.removePhotoFromDatabase(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photoViewModel.getAllPhoto()

        photoViewModel.isLoading.observe(viewLifecycleOwner, {
            binding?.photoListProgress?.isVisible = it
        })

        photoViewModel.photoData.observe(viewLifecycleOwner, { photoData ->
            photoAdapter.setPhotos(photoData.first)
            when (photoData.second) {
                CrudOperations.GET -> {
                    photoAdapter.notifyItemRangeInserted(0, photoData.first.size)
                }
                CrudOperations.REMOVE -> {
                    photoAdapter.notifyItemRemoved(removePosition)
                }
                CrudOperations.ADD -> {
                    photoAdapter.notifyItemInserted(photoData.first.lastIndex)
                }
            }
        })

        photoViewModel.errorRequest.observe(viewLifecycleOwner, { crudOperation ->
            if (crudOperation == null) snackBarIndefinite(R.string.request_error)
            else {
                snackBarLong(
                    when (crudOperation) {
                        CrudOperations.GET -> R.string.recover_error
                        CrudOperations.REMOVE -> R.string.remove_error
                        CrudOperations.ADD -> R.string.insert_error
                    }
                )
            }
        })

        binding?.photoCamera?.setOnClickListener {
            takePhoto()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val COLUMNS_NUMBER = 3
        const val DEFAULT_ITEM_OFFSET = 8
        fun newInstance() = PhotoFragment()
        private const val PROVIDER = "provider"
        private const val PREFIX_IMG_NAME = "IMG"
        private const val SEPARATOR_IMG_NAME = "_"
        private const val IMG_EXTENSION = ".jpg"
    }
}
