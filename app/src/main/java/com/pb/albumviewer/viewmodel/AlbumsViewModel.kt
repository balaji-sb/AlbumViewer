package com.pb.albumviewer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pb.albumviewer.base.BaseApplication
import com.pb.albumviewer.model.response.Albums
import com.pb.albumviewer.repository.AlbumRepository
import kotlinx.coroutines.*

/**
 * Created by balaji on 12/9/20 9:40 AM
 */


class AlbumsViewModel : ViewModel() {

    private val coroutineJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Default + coroutineJob)
    private val albumRepository = AlbumRepository(BaseApplication.mContext)

    private var albumResponse = MutableLiveData<List<Albums>>()

    fun fetchAlbums() {
        coroutineScope.launch {
            try {
                albumRepository.fetchAlbums()
            } catch (th: Throwable) {
                th.printStackTrace()
            }
            val coroutineScope = CoroutineScope(Dispatchers.Main + coroutineJob)
            coroutineScope.launch {
                albumResponse.value = albumRepository.getAlbums()
            }
            this.cancel()
        }
    }

    fun getAlbumCount(): MutableLiveData<Int> {
        val liveData = MutableLiveData<Int>()
        val coroutineScope = CoroutineScope(Dispatchers.Main + coroutineJob)
        coroutineScope.launch {
            liveData.value = albumRepository.getAlbumsCount()
        }
        return liveData
    }

    fun getAlbums(): LiveData<List<Albums>>? {
        return albumResponse
    }

    override fun onCleared() {
        super.onCleared()
    }
}