package com.pb.albumviewer.repository

import com.pb.albumviewer.base.BaseApplication
import com.pb.albumviewer.db.AlbumDatabase
import com.pb.albumviewer.model.response.Albums
import com.pb.albumviewer.restclient.RetrofitService

/**
 * Created by balaji on 12/9/20 9:34 AM
 */


object AlbumRepository {

    private var albumApi = RetrofitService.createService(AlbumApi::class.java)

    private var albumDatabase = AlbumDatabase.getInstance(BaseApplication.mContext)

    private val albumDao = albumDatabase.getAlbumsDao()

    suspend fun fetchPhotosByAlbum(albumId: Int) =
        albumApi.fetchPhotosByAlbum(albumId)

    suspend fun fetchAlbums() {
        val albums = albumApi.fetchAlbums()
        albumDao.insertAlbums(albums)
    }

    suspend fun getAlbums() = albumDao.getAlbums()

    suspend fun getAlbumsCount() = albumDao.getAlbumsCount()

    suspend fun getAlbumsByTitle(title: String): List<Albums> = albumDao.searchFromAlbum(title)

}