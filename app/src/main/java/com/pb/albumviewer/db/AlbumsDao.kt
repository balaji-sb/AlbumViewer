package com.pb.albumviewer.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pb.albumviewer.model.response.Albums

/**
 * Created by balaji on 12/9/20 8:24 PM
 */

@Dao
interface AlbumsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albumsList: List<Albums>)

    @Query("SELECT * FROM albums")
    suspend fun getAlbums(): List<Albums>

    @Query("SELECT count(*) FROM albums")
    suspend fun getAlbumsCount(): Int

}