package com.pb.albumviewer.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pb.albumviewer.R
import com.pb.albumviewer.interfaces.OnItemClickedListener
import com.pb.albumviewer.model.response.Albums
import kotlinx.android.synthetic.main.child_album.view.*

/**
 * Created by balaji on 12/9/20 11:30 AM
 */


class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder>() {

    private var albumsList: List<Albums> = emptyList()
    private var mOnItemClickedListener: OnItemClickedListener? = null

    fun setOnItemClickListener(lOnItemClickedListener: OnItemClickedListener?) {
        this.mOnItemClickedListener = lOnItemClickedListener
    }

    fun setAlbums(lAlbumsList: List<Albums>) {
        this.albumsList = lAlbumsList
        notifyDataSetChanged()
    }

    inner class AlbumsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(album: Albums) {
            itemView.albumName.text = album.album_title
            itemView.setOnClickListener {
                mOnItemClickedListener?.onItemClicked(album)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        try {
            val view = getContentView(parent, R.layout.child_album)
            return AlbumsViewHolder(view)
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw Exception("View must not be null")
        }
    }

    override fun getItemCount(): Int {
        return albumsList.size
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        holder.bindItems(albumsList[position])
    }

    private fun getContentView(parent: ViewGroup, layout: Int): View {
        return LayoutInflater.from(parent.context).inflate(layout, parent, false)
    }
}