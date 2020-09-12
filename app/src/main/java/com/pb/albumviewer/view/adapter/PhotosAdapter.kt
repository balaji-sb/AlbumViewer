package com.pb.albumviewer.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pb.albumviewer.R
import com.pb.albumviewer.interfaces.OnItemClickedListener
import com.pb.albumviewer.model.response.Photos
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.child_photos.view.*

/**
 * Created by balaji on 12/9/20 11:30 AM
 */


class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    private var photosList: List<Photos> = emptyList()
    private lateinit var context: Context
    private var mOnItemClickedListener: OnItemClickedListener? = null

    fun setOnItemClickListener(lOnItemClickedListener: OnItemClickedListener?) {
        this.mOnItemClickedListener = lOnItemClickedListener
    }

    fun setPhotos(lPhotosList: List<Photos>) {
        this.photosList = lPhotosList
        notifyDataSetChanged()
    }

    inner class PhotosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(photo: Photos) {
            Picasso.get().load(photo.thumbnailUrl).into(itemView.photoImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        try {
            val view = getContentView(parent, R.layout.child_photos)
            return PhotosViewHolder(view)
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw Exception("View must not be null")
        }
    }

    override fun getItemCount(): Int {
        return photosList.size
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.bindItems(photosList[position])
    }

    private fun getContentView(parent: ViewGroup, layout: Int): View {
        context = parent.context
        return LayoutInflater.from(context).inflate(layout, parent, false)
    }
}