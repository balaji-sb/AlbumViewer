package com.pb.albumviewer.base

import android.app.Application
import android.content.Context
import com.pb.albumviewer.R
import com.pb.albumviewer.utils.Const

/**
 * Created by balaji on 12/9/20 9:17 AM
 */


class BaseApplication : Application() {

    companion object {
        lateinit var mContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        Const.frameId = R.id.homeFrame
    }

}