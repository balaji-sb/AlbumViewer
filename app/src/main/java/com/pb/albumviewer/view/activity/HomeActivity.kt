package com.pb.albumviewer.view.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import com.pb.albumviewer.R
import com.pb.albumviewer.base.BaseActivity
import com.pb.albumviewer.utils.PackageUtils
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
        searchImg.contentDescription = getString(R.string.search_close)
        searchImg.setOnClickListener(this)
        searchEdt.addTextChangedListener(this)
    }

    override fun setupViews() {
        searchEdt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                PackageUtils.hideKeyboard(this)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

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
                if(searchImg.contentDescription ==  getString(R.string.search_open)){
                    searchEdt.visibility=View.GONE
                    searchImg.setImageResource(R.drawable.ic_baseline_search_24)
                    searchImg.contentDescription = getString(R.string.search_close)
                    callSearch("")
                }else{
                    searchEdt.visibility = View.VISIBLE
                    searchImg.setImageResource(R.drawable.ic_baseline_close_24)
                    searchImg.contentDescription = getString(R.string.search_open)
                }

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
            callSearch(it)
        }
    }

    private fun callSearch(it: CharSequence) {
        val currentFragment = getCurrentFragment()
        if (currentFragment is AlbumsFragment) {
            currentFragment.searchItem(it.toString())
        }
    }
}