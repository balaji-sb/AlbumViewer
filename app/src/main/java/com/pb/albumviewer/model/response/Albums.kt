package com.pb.albumviewer.model.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by balaji on 12/9/20 9:33 AM
 */

@Entity(tableName = "albums")
data class Albums(

    @ColumnInfo(name = "user_id")
    @SerializedName("userId")
    val user_id: Int,

    @PrimaryKey
    @ColumnInfo(name = "album_id")
    @SerializedName("id")
    val album_id: Int,

    @ColumnInfo(name = "album_title")
    @SerializedName("title")
    val album_title: String
)
