package com.pb.albumviewer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pb.albumviewer.base.BaseApplication
import com.pb.albumviewer.model.response.Photos
import com.pb.albumviewer.repository.AlbumRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by balaji on 12/9/20 10:07 AM
 */


class PhotosViewModel : ViewModel() {

    private val coroutineJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + coroutineJob)

    private val albumRepository = AlbumRepository(BaseApplication.mContext)

    fun fetchPhotosById(albumId: Int): LiveData<List<Photos>> {
        val photosList = MutableLiveData<List<Photos>>()
        coroutineScope.launch {
            try {
                val albumsResponse = albumRepository.fetchPhotosByAlbum(albumId)
                val coroutineScope = CoroutineScope(Dispatchers.Main + coroutineJob)
                coroutineScope.launch {
                    photosList.value = albumsResponse
                }
            } catch (th: Throwable) {
                th.printStackTrace()
            }
        }
        return photosList
    }

}