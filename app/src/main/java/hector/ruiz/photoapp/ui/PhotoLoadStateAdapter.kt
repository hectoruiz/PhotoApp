package hector.ruiz.photoapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import hector.ruiz.photoapp.R
import hector.ruiz.photoapp.databinding.LoadStatePhotoBinding

class PhotoLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<PhotoLoadStateAdapter.PhotoLoadStateViewHolder>() {

    class PhotoLoadStateViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = LoadStatePhotoBinding.bind(view)

        fun bind(loadState: LoadState, retry: () -> Unit) {
            binding.apply {
                binding.loadStateRetry.setOnClickListener {
                    retry.invoke()
                }
                this.loadStateRetry.isVisible = loadState !is LoadState.Loading
                this.loadStateError.isVisible = loadState !is LoadState.Loading
                this.loadStateProgress.isVisible = loadState is LoadState.Loading

                if (loadState is LoadState.Error) {
                    this.loadStateError.text = view.resources.getString(R.string.error_request)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: PhotoLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PhotoLoadStateViewHolder {
        return PhotoLoadStateViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.load_state_photo, parent, false)
        )
    }
}
