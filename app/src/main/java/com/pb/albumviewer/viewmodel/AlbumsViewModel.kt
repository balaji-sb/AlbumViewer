package com.pb.albumviewer.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pb.albumviewer.model.response.Albums
import com.pb.albumviewer.repository.AlbumRepository
import kotlinx.coroutines.*
import java.lang.Exception

/**
 * Created by balaji on 12/9/20 9:40 AM
 */


class AlbumsViewModel : ViewModel() {

    private val coroutineJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Default + coroutineJob)

    fun fetchAlbums(): LiveData<List<Albums>> {
        var albumsList = MutableLiveData<List<Albums>>()
        coroutineScope.launch {
            try {
                AlbumRepository.fetchAlbums()
            } catch (th: Throwable) {
                th.printStackTrace()
            }
            val albumsResponse = AlbumRepository.getAlbums()
            val coroutineScope = CoroutineScope(Dispatchers.Main + coroutineJob)
            coroutineScope.launch {
                albumsList.value = albumsResponse
            }
            Log.e("Search Result", albumsList.value?.size.toString())
        }
        return albumsList
    }

    fun searchItem(searchValue: String): MutableLiveData<List<Albums>> {
        val searchList = MutableLiveData<List<Albums>>()
        var list = listOf<Albums>()
        coroutineScope.launch {
            try {
                list = AlbumRepository.getAlbumsByTitle(searchValue)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

            val coroutineScope = CoroutineScope(Dispatchers.Main + coroutineJob)
            coroutineScope.launch {
                searchList.value = list
            }
        }
        return searchList
    }

    fun getAlbumCount(): MutableLiveData<Int> {
        val liveData = MutableLiveData<Int>()
        val coroutineScope = CoroutineScope(Dispatchers.Main + coroutineJob)
        coroutineScope.launch {
            liveData.value = AlbumRepository.getAlbumsCount()
        }
        return liveData
    }

    override fun onCleared() {
        super.onCleared()
    }
}