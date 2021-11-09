package hector.ruiz.photoapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hector.ruiz.domain.PhotoUi
import hector.ruiz.photoapp.R
import hector.ruiz.photoapp.databinding.ItemPhotoBinding
import javax.inject.Inject

class PhotoAdapter @Inject constructor(private val picasso: Picasso) :
    PagingDataAdapter<PhotoUi, PhotoAdapter.PhotoViewHolder>(DiffUtilCallback()) {

    var onDeleteClick: ((PhotoUi?) -> Unit)? = null

    inner class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemPhotoBinding.bind(view)

        init {
            /*binding.characterDetail.setOnClickListener {
                onDeleteClick?.invoke(getItem(bindingAdapterPosition))
            }*/
        }

        fun bind(photoUi: PhotoUi?) {
            /*with(binding) {
                characterProgress.isVisible = true
                picasso.loadImage(
                    photoUi?.path,
                    this.photoImage,
                    this.photoProgress
                )
                this.photoDate = photoUi?.date
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

    class DiffUtilCallback : DiffUtil.ItemCallback<PhotoUi>() {
        override fun areItemsTheSame(oldItem: PhotoUi, newItem: PhotoUi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PhotoUi, newItem: PhotoUi): Boolean {
            return oldItem == newItem
        }
    }
}
