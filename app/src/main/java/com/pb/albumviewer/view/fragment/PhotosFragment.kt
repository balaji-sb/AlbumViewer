package com.pb.albumviewer.view.fragment

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.pb.albumviewer.R
import com.pb.albumviewer.base.BaseFragment
import com.pb.albumviewer.model.response.Photos
import com.pb.albumviewer.utils.Const
import com.pb.albumviewer.view.adapter.PhotosAdapter
import com.pb.albumviewer.viewmodel.PhotosViewModel
import kotlinx.android.synthetic.main.fragment_albums.*

/**
 * Created by balaji on 12/9/20 4:53 PM
 */


class PhotosFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var mPhotosViewModel: PhotosViewModel
    private var mPhotosAdapter: PhotosAdapter? = null
    private var mGridLayoutManager: GridLayoutManager? = null
    private var albumId = 1

    override fun getLayoutResource(): Int {
        return R.layout.fragment_albums
    }

    override fun getScreenName(): String? {
        return fetchScreenName(activity)
    }

    override fun initValues() {
        mPhotosViewModel = ViewModelProvider(this)[PhotosViewModel::class.java]
        mPhotosAdapter = PhotosAdapter()
        mGridLayoutManager = GridLayoutManager(this.context, Const.ALBUM_GRID_COUNT)
        arguments?.let {
            albumId = it.getInt(Const.ALBUM_ID, 1)
        }
    }

    override fun setUpViews() {
        val albumPhotoRecyclerView =
            albumPhotoRecycler.findViewById<RecyclerView>(R.id.genericRecycler)
        albumPhotoSwipeRefresh.setOnRefreshListener(this)
        albumPhotoRecyclerView?.apply {
            layoutManager = mGridLayoutManager
            adapter = mPhotosAdapter
        }
        fetchAlbums()
    }

    override fun onRefresh() {

    }

    private fun fetchAlbums() {
        if (!albumPhotoSwipeRefresh.isRefreshing)
            showProgressBar(true)
        mPhotosViewModel.fetchPhotosById(albumId).observe(this, photosObserver)
    }

    private val photosObserver = Observer<List<Photos>> { photosList ->
        photosList?.apply {
            if (this.isNotEmpty()) {
                showProgressBar(false)
                albumPhotoSwipeRefresh.isRefreshing = false
                mPhotosAdapter?.setPhotos(photosList)
            }
        }
    }

    private fun showProgressBar(progressVisibility: Boolean) {
        if (progressVisibility) albumPhotoProgress.visibility = View.VISIBLE
        else albumPhotoProgress.visibility = View.GONE
    }
}