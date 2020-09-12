package com.pb.albumviewer.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.pb.albumviewer.utils.Const
import com.pb.albumviewer.utils.PackageUtils
import kotlin.reflect.KClass

/**
 * Created by balaji on 12/9/20 9:16 AM
 */


abstract class BaseActivity: AppCompatActivity(),View.OnClickListener {

    private var mContext: Context? = null
    var TAG = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mContext = this
        setContentView(getLayoutResource())
        TAG = getScreenName()
        initValues()
        setupViews()
    }

    /**
     * navigate activity for classes whoever extends Appcompatactivity
     */

    fun navigateActivity(
        activity: KClass<out AppCompatActivity>,
        bundle: Bundle = Bundle(),
        isNewActivity: Boolean = false
    ) {
        try {
            val intent = Intent(mContext, activity.java)
            if (isNewActivity)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtras(bundle)
            mContext?.startActivity(intent)
            PackageUtils.hideKeyboard(activity.objectInstance)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    /**
     * Navigate fragment with backstack
     */

    fun navigateFragmentWithBackStack(fragment: Fragment, bundle: Bundle = Bundle()) {
        mContext?.let {
            val fragmentManager = (it as FragmentActivity).supportFragmentManager
            var transaction = fragmentManager.beginTransaction()
            transaction.addToBackStack(fragment.javaClass.simpleName)
            fragment.arguments = bundle
            transaction =
                transaction.replace(Const.frameId, fragment, fragment.javaClass.simpleName)
            transaction.commit()
        }
    }

    /**
     * Navigate fragment without backstack
     */

    fun navigateFragment(fragment: Fragment, bundle: Bundle = Bundle()) {
        mContext?.let {
            val fragmentManager = (it as FragmentActivity).supportFragmentManager
            var transaction = fragmentManager.beginTransaction()
            fragment.arguments = bundle
            transaction =
                transaction.replace(Const.frameId, fragment, fragment.javaClass.simpleName)
            transaction.commit()
        }
    }

    /**
     * Get current fragment
     *
     * @return
     */

    fun getCurrentFragment(): Fragment? {
        val fragmentManager: FragmentManager? = supportFragmentManager
        return fragmentManager?.findFragmentById(Const.frameId)
    }

    fun fetchScreenName(activity: Activity): String {
        return activity.javaClass.simpleName
    }

    override fun onClick(view: View?) {
        // do nothing
    }

    abstract fun getLayoutResource(): Int

    abstract fun getScreenName(): String

    abstract fun initValues()

    abstract fun setupViews()
}