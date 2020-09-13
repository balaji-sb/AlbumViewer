package com.pb.albumviewer.view.fragment

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.pb.albumviewer.R
import com.pb.albumviewer.base.BaseFragment
import com.pb.albumviewer.interfaces.OnItemClickedListener
import com.pb.albumviewer.model.response.Albums
import com.pb.albumviewer.utils.Const
import com.pb.albumviewer.utils.Const.ALBUM_GRID_COUNT
import com.pb.albumviewer.view.adapter.AlbumsAdapter
import com.pb.albumviewer.viewmodel.AlbumsViewModel
import kotlinx.android.synthetic.main.fragment_albums.*

/**
 * Created by balaji on 12/9/20 11:24 AM
 */


class AlbumsFragment : BaseFragment(), OnItemClickedListener, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var mAlbumsViewModel: AlbumsViewModel
    private var mAlbumsAdapter: AlbumsAdapter? = null
    private var mGridLayoutManager: GridLayoutManager? = null
    private var mAlbumsList: List<Albums> = arrayListOf<Albums>()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_albums
    }

    override fun getScreenName(): String? {
        return fetchScreenName(activity)
    }

    override fun initValues() {
        mAlbumsViewModel = ViewModelProvider(this)[AlbumsViewModel::class.java]
        mAlbumsAdapter = AlbumsAdapter()
        mGridLayoutManager = GridLayoutManager(this.context, ALBUM_GRID_COUNT)
    }

    override fun setUpViews() {
        val albumPhotoRecyclerView =
            albumPhotoRecycler.findViewById<RecyclerView>(R.id.genericRecycler)
        mAlbumsAdapter?.setOnItemClickListener(this)
        albumPhotoSwipeRefresh.setOnRefreshListener(this)
        albumPhotoRecyclerView?.apply {
            layoutManager = mGridLayoutManager
            adapter = mAlbumsAdapter
        }
        fetchAlbums()
    }

    override fun onItemClicked(model: Any) {
        if (model is Albums) {
            val bundle = Bundle()
            bundle.putInt(Const.ALBUM_ID, model.album_id)
            navigateFragmentWithBackStack(PhotosFragment(), bundle)
        }
    }

    override fun onRefresh() {
        fetchAlbums()
    }

    private fun fetchAlbums() {
        if (!albumPhotoSwipeRefresh.isRefreshing)
            showProgressBar(true)
        mAlbumsViewModel.fetchAlbums()
        mAlbumsViewModel.getAlbums()?.observe(this, albumsObserver)
    }

    private val albumsObserver = Observer<List<Albums>> { albumsList ->
        albumsList?.apply {
            mAlbumsList = this
            if (this.isNotEmpty()) {
                showProgressBar(false)
                albumPhotoSwipeRefresh.isRefreshing = false
                updateAdapter(mAlbumsList)
            }
        }
    }

    fun searchItem(searchValue: String) {
        if (mAlbumsList.isEmpty() || searchValue.isEmpty()) {
            updateAdapter(mAlbumsList)
        } else {
            val tempList = arrayListOf<Albums>()
            mAlbumsList.forEach {
                if (it.album_title.contains(searchValue)) {
                    tempList.add(it)
                }
            }
            updateAdapter(tempList)
        }
    }

    private fun updateAdapter(albumList: List<Albums>) {
        val albumPhotoRecyclerView =
            albumPhotoRecycler.findViewById<RecyclerView>(R.id.genericRecycler)
        val noRecordsLayout =
            albumPhotoRecycler.findViewById<ConstraintLayout>(R.id.noRecordsLayout)
        if (albumList.isEmpty()) {
            noRecordsLayout.visibility = View.VISIBLE
            albumPhotoRecyclerView.visibility = View.GONE
        } else {
            noRecordsLayout.visibility = View.GONE
            albumPhotoRecyclerView.visibility = View.VISIBLE
            mAlbumsAdapter?.setAlbums(albumList)
        }
    }

    private fun showProgressBar(progressVisibility: Boolean) {
        if (progressVisibility) albumPhotoProgress.visibility = View.VISIBLE
        else albumPhotoProgress.visibility = View.GONE
    }

}