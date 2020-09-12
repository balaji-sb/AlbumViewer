package com.pb.albumviewer.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.pb.albumviewer.utils.Const.ONE
import com.pb.albumviewer.utils.Const.ZERO

/**
 * Created by balaji on 12/9/20 9:19 AM
 */


object PackageUtils {

    /**
     * Show soft keyboard
     */
    fun showKeyboard(activity: Activity?) {
        activity?.let {
            val view: View? = it.currentFocus
            if (null != view && null != view.windowToken && EditText::class.java.isAssignableFrom(
                    view.javaClass
                )
            ) {
                val inputMethodManager =
                    it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, ONE)
            } else {
                it.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            }
        }
    }

    /**
     * Hide soft keyboard
     */

    fun hideKeyboard(activity: Activity?) {
        activity?.let {
            val inputMethodManager =
                it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val view = it.currentFocus
            if (null != view && null != view.windowToken && EditText::class.java.isAssignableFrom(
                    view.javaClass
                )
            ) {
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, ZERO)
            } else {
                it.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            }
        }
    }

    /**
     * Check internet connection
     */

    fun isInternetAvailable(context: Context?): Boolean {
        var isConnected = false
        try {
            val connectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkInfo =
                    connectivityManager.activeNetwork
                val activeNetwork = connectivityManager.getNetworkCapabilities(networkInfo)
                activeNetwork?.apply {
                    isConnected = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        else -> false
                    }
                }

            } else {
                connectivityManager.apply {
                    activeNetworkInfo?.apply {
                        isConnected = when (type) {
                            ConnectivityManager.TYPE_WIFI -> true
                            ConnectivityManager.TYPE_MOBILE -> true
                            ConnectivityManager.TYPE_ETHERNET -> true
                            else -> false
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return isConnected
    }

}