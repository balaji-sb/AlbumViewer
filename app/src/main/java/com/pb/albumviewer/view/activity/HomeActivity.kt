package com.pb.albumviewer.view.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.pb.albumviewer.R
import com.pb.albumviewer.base.BaseActivity
import com.pb.albumviewer.view.fragment.AlbumsFragment
import com.pb.albumviewer.view.fragment.SplashFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_header.*

/**
 * Created by balaji on 12/9/20 11:18 AM
 */


class HomeActivity : BaseActivity(), TextWatcher {

    override fun getLayoutResource(): Int {
        return R.layout.activity_home
    }

    override fun getScreenName(): String {
        return fetchScreenName(this)
    }

    override fun initValues() {
        searchImg.setOnClickListener(this)
        searchEdt.addTextChangedListener(this)
    }

    override fun setupViews() {
        val splashFragment = SplashFragment()
        navigateFragment(splashFragment)
    }

    fun hideToolbar() {
        toolbarLayout?.visibility = View.GONE
    }

    fun showToolbar() {
        toolbarLayout?.visibility = View.VISIBLE
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.searchImg -> {
                searchEdt.visibility = View.VISIBLE
            }
        }
    }

    override fun afterTextChanged(p0: Editable?) {
        //do nothing
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        //do nothing
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        p0?.let {
            val currentFragment = getCurrentFragment()
            if (currentFragment is AlbumsFragment) {
                currentFragment.searchItem(it.toString())
            }
        }
    }
}