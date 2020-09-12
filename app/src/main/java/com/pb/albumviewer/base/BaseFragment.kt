package com.pb.albumviewer.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.pb.albumviewer.interfaces.NetworkChangeListener
import com.pb.albumviewer.utils.Const
import com.pb.albumviewer.utils.NetworkChangeReceiver
import com.pb.albumviewer.view.activity.HomeActivity
import com.pb.albumviewer.view.fragment.NoInternetFragment

/**
 * Created by balaji on 12/9/20 11:11 AM
 */


abstract class BaseFragment : Fragment(), NetworkChangeListener, View.OnClickListener {

    var TAG = ""
    var mView: View? = null
    var mNetworkChangeReceiver: NetworkChangeReceiver? = null

    override fun onStart() {
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = getScreenName() ?: this.javaClass.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(getLayoutResource(), container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.mView = view
        mNetworkChangeReceiver = NetworkChangeReceiver()
        initValues()
        setUpViews()
    }

    private fun networkConnectIntentFilter(): IntentFilter {
        return IntentFilter().apply {
            addAction("android.net.conn.CONNECTIVITY_CHANGE")
            addAction("android.net.wifi.WIFI_STATE_CHANGED")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume")
        mNetworkChangeReceiver?.registerListener(this)
        activity?.registerReceiver(mNetworkChangeReceiver, networkConnectIntentFilter())
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG, "onPause")
        mNetworkChangeReceiver?.unregisterListener()
        activity?.unregisterReceiver(mNetworkChangeReceiver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e(TAG, "onDestroyView")
    }

    override fun onStop() {
        super.onStop()
        Log.e(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e(TAG, "onDetach")
    }

    /**
     * Get current fragment
     *
     * @return
     */

    fun getCurrentFragment(): Fragment? {
        val fragmentManager: FragmentManager? = activity?.supportFragmentManager
        return fragmentManager?.findFragmentById(Const.frameId)
    }

    /**
     * Navigate to previous fragment
     */

    fun navigateToPreviousFragment() {
        activity?.supportFragmentManager?.popBackStackImmediate()
    }

    /**
     * Navigate fragment with backstack
     */

    fun navigateFragmentWithBackStack(fragment: Fragment, bundle: Bundle = Bundle()) {
        activity?.let {
            if (it is BaseActivity) it.navigateFragmentWithBackStack(fragment, bundle)
        }
    }

    /**
     * Navigate fragment without backstack
     */

    fun navigateFragment(fragment: Fragment, bundle: Bundle = Bundle()) {
        activity?.let {
            if (it is BaseActivity) it.navigateFragment(fragment, bundle)
        }
    }

    fun fetchScreenName(activity: Activity?): String? {
        return activity?.javaClass?.simpleName
    }

    open fun onBackPressed(): Boolean {
        return false
    }

    fun finish() {
        activity?.supportFragmentManager?.popBackStack()
    }

    override fun onNetworkConnectedStatus(status: Boolean) {
        Log.e(TAG, "onNetworkConnectedStatus $status")
        if (!status) {
            if (getCurrentFragment() !is NoInternetFragment)
                navigateFragmentWithBackStack(NoInternetFragment())
        } else {
            if (getCurrentFragment() is NoInternetFragment)
                finish()
        }

    }

    fun hideToolbar() {
        activity?.let {
            if (it is HomeActivity) {
                it.hideToolbar()
            }
        }
    }

    fun showToolbar() {
        activity?.let {
            if (it is HomeActivity) {
                it.showToolbar()
            }
        }
    }

    override fun onClick(view: View?) {
    }

    abstract fun getLayoutResource(): Int

    abstract fun getScreenName(): String?

    abstract fun initValues()

    abstract fun setUpViews()

}