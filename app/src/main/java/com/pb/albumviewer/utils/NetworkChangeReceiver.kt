package com.pb.albumviewer.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.pb.albumviewer.interfaces.NetworkChangeListener

/**
 * Created by balaji on 12/9/20 11:44 AM
 */


class NetworkChangeReceiver : BroadcastReceiver() {

    private var mNetworkChangeListener: NetworkChangeListener? = null

    fun registerListener(lNetworkChangeListener: NetworkChangeListener) {
        this.mNetworkChangeListener = lNetworkChangeListener
    }

    fun unregisterListener() {
        this.mNetworkChangeListener = null
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("OnReceive","Onreceive triggered")
        val isConnected = PackageUtils.isInternetAvailable(context)
        mNetworkChangeListener?.onNetworkConnectedStatus(isConnected)
    }
}