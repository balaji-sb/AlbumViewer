package com.pb.albumviewer.view.fragment

import android.view.View
import com.pb.albumviewer.R
import com.pb.albumviewer.base.BaseFragment
import com.pb.albumviewer.utils.PackageUtils
import kotlinx.android.synthetic.main.fragment_no_internet.*

/**
 * Created by balaji on 12/9/20 12:06 PM
 */


class NoInternetFragment : BaseFragment() {

    override fun getLayoutResource(): Int {
        return R.layout.fragment_no_internet
    }

    override fun getScreenName(): String? {
        return fetchScreenName(activity)
    }

    override fun initValues() {
        //do nothing
    }

    override fun setUpViews() {
        btnRetry.setOnClickListener(this)
    }

    private fun checkConnectionAndFetch() {
        if (isInternetConnected()) {
            finish()
        }
    }


    private fun isInternetConnected(): Boolean {
        return PackageUtils.isInternetAvailable(this.context)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnRetry -> checkConnectionAndFetch()
        }
    }


}