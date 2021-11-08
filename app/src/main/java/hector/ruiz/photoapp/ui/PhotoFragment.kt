package hector.ruiz.photoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import hector.ruiz.photoapp.databinding.FragmentPhotoBinding
import javax.inject.Inject

@AndroidEntryPoint
class PhotoFragment : Fragment() {

    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var photoAdapter: PhotoAdapter

    @Inject
    lateinit var picasso: Picasso

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
        photoAdapter.onDeleteClick = { photoId ->
            photoId?.let {
                // TODO
                // Call view model to remove the photo id selected
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.fab?.setOnClickListener {
            // TODO
            // Manage camera and storage permissions
            // Call view model to add the photo id selected
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = PhotoFragment()
    }
}
