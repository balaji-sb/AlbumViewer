package com.pb.albumviewer.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pb.albumviewer.model.response.Albums
import kotlinx.coroutines.CoroutineScope

/**
 * Created by balaji on 12/9/20 8:22 PM
 */


@Database(entities = [Albums::class], version = 1, exportSchema =false)
abstract class AlbumDatabase : RoomDatabase() {

    abstract fun getAlbumsDao():AlbumsDao

    companion object {
        @Volatile
        private var INSTANCE: AlbumDatabase? = null

        fun getInstance(context: Context): AlbumDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(context.applicationContext, AlbumDatabase::class.java, "album_db")
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }

}