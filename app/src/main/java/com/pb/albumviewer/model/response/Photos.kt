package com.pb.albumviewer.model.response

import com.google.gson.annotations.SerializedName

/**
 * Created by balaji on 12/9/20 9:31 AM
 */


data class Photos(

    @SerializedName("albumId")
    val albumId: Int,

    @SerializedName("id")
    val photoId: Int,

    @SerializedName("title")
    val albumTitle: String,

    @SerializedName("url")
    val imageUrl: String,

    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String


)