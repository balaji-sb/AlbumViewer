package com.pb.albumviewer.repository

import com.pb.albumviewer.model.response.APIResponse
import com.pb.albumviewer.model.response.Albums
import com.pb.albumviewer.model.response.Photos
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by balaji on 12/9/20 9:35 AM
 */


interface AlbumApi {

    @GET("/users/1/albums")
    suspend fun fetchAlbums(): List<Albums>

    @GET("/albums/{albumId}/photos")
    suspend fun fetchPhotosByAlbum(@Path("albumId") albumId: Int): List<Photos>

}