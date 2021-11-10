package hector.ruiz.photoapp.ui.adapter

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hector.ruiz.commons.extensions.loadImage
import hector.ruiz.domain.PhotoUi
import hector.ruiz.photoapp.R
import hector.ruiz.photoapp.databinding.ItemPhotoBinding
import java.io.File
import javax.inject.Inject

class PhotoAdapter @Inject constructor(private val picasso: Picasso) :
    RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    var onDeleteClick: ((PhotoUi?, Int) -> Unit)? = null
    private var photos: List<PhotoUi?> = emptyList()

    fun setPhotos(photoList: List<PhotoUi?>) {
        this.photos = photoList
    }

    inner class PhotoViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemPhotoBinding.bind(view)

        init {
            binding.photoButtonRemove.setOnClickListener {
                onDeleteClick?.invoke(photos[adapterPosition], adapterPosition)
            }
        }

        fun bind(photoUi: PhotoUi?) {
            with(binding) {
                picasso.loadImage(
                    getImageUri(view.context, photoUi?.path),
                    this.photoImage,
                    this.photoImageProgress
                )
                this.photoButtonRemove.visibility = View.VISIBLE
            }
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
        val photo = photos[position]
        holder.bind(photo)
    }

    override fun getItemCount(): Int = photos.size

    private fun getImageUri(context: Context, pathImage: String?): Uri? {
        return Uri.parse(pathImage).let { uri ->
            context.contentResolver?.query(
                uri, null, null,
                null, null
            )?.let { cursor ->
                val nameFileIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                cursor.moveToFirst()
                val imageUri = File(context.filesDir, cursor.getString(nameFileIndex)).toUri()
                cursor.close()
                imageUri
            }
        }
    }
}
