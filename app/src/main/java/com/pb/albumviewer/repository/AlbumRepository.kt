package com.pb.albumviewer.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pb.albumviewer.base.BaseApplication
import com.pb.albumviewer.db.AlbumDatabase
import com.pb.albumviewer.model.response.Albums
import com.pb.albumviewer.restclient.RetrofitService

/**
 * Created by balaji on 12/9/20 9:34 AM
 */


class AlbumRepository(application: Context) {

    private var albumApi = RetrofitService.createService(AlbumApi::class.java)

    private var albumDatabase = AlbumDatabase.getInstance(application)

    private val albumDao = albumDatabase.getAlbumsDao()

    private var albumLiveData: List<Albums>? = arrayListOf()

    suspend fun fetchPhotosByAlbum(albumId: Int) =
        albumApi.fetchPhotosByAlbum(albumId)

    suspend fun fetchAlbums() {
        val albums = albumApi.fetchAlbums()
        albumDao.insertAlbums(albums)
        albumLiveData = albumDao.getAlbums()
    }

    suspend fun getAlbumsCount() = albumDao.getAlbumsCount()

    fun getAlbums(): List<Albums> {
        return albumLiveData!!
    }

}