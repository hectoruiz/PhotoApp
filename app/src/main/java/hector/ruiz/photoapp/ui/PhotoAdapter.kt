package hector.ruiz.photoapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hector.ruiz.domain.Photo
import hector.ruiz.photoapp.R
import hector.ruiz.photoapp.databinding.ItemPhotoBinding
import javax.inject.Inject

class PhotoAdapter @Inject constructor(private val picasso: Picasso) :
    PagingDataAdapter<Photo, PhotoAdapter.PhotoViewHolder>(DiffUtilCallback()) {

    var onDeleteClick: ((String?) -> Unit)? = null

    inner class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemPhotoBinding.bind(view)

        init {
            /*binding.characterDetail.setOnClickListener {
                onDeleteClick?.invoke(getItem(bindingAdapterPosition)?.id)
            }*/
        }

        fun bind(photo: Photo?) {
            /*with(binding) {
                characterProgress.isVisible = true
                picasso.loadImage(
                    photo?.path,
                    this.photoImage,
                    this.photoProgress
                )
                this.photoDate = photo?.date
            }*/
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PhotoViewHolder(
            layoutInflater.inflate(
                R.layout.item_photo,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = getItem(position)
        holder.bind(photo)
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }
}
