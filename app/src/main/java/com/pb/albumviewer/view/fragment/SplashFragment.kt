package com.pb.albumviewer.view.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.pb.albumviewer.R
import com.pb.albumviewer.base.BaseFragment
import com.pb.albumviewer.utils.Const

/**
 * Created by balaji on 12/9/20 11:21 AM
 */


class SplashFragment : BaseFragment() {

    private val handler by lazy { Handler(Looper.myLooper()!!) }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_splash
    }

    override fun getScreenName(): String? {
        return fetchScreenName(activity)
    }

    override fun initValues() {
        //do nothing
    }

    override fun setUpViews() {
        handler.postDelayed(runnable, Const.SPLASH_INTERVAL)
    }

    private val runnable = Runnable {
        navigateFragment(AlbumsFragment())
    }

    override fun onResume() {
        super.onResume()
        setUpViews()
        hideToolbar()
    }

    override fun onPause() {
        super.onPause()
        handler.apply {
            removeCallbacks(runnable)
        }
        showToolbar()
    }
}