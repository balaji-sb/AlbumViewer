package com.pb.albumviewer.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by balaji on 12/9/20 8:25 PM
 */

@Entity(tableName = "albums")
data class AlbumsModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int?,

    @ColumnInfo(name = "userId") val userId: Int?,

    @ColumnInfo(name = "albumId") val albumId: Int?,

    @ColumnInfo(name = "albumTitle") val albumTitle: String?

)