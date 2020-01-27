package com.cyberinsane.twowayviewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_vertical_pager.view.*

class VerticalPagerFragment : Fragment() {

    companion object {
        fun newInstance(): VerticalPagerFragment {
            return VerticalPagerFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_vertical_pager, container, false)
        initVerticalPager(view, isCreate = true, showAnnouncement = true)
        return view
    }

    fun toggleAnnouncement() {
        if (view?.verticalPager?.adapter?.itemCount == 2) {
            showAnnouncement()
        } else {
            removeAnnouncement()
        }
    }

    private fun showAnnouncement() {
        view?.let {
            initVerticalPager(it, isCreate = false, showAnnouncement = true)
        }
    }

    private fun removeAnnouncement() {
        view?.let {
            initVerticalPager(it, isCreate = false, showAnnouncement = false)
        }
    }

    fun navigateToAnnouncement() {
        view?.verticalPager?.setCurrentItem(0, true)
    }

    fun navigateToHome() {
        view?.verticalPager?.setCurrentItem(1, true)
    }

    fun navigateToMenu() {
        view?.verticalPager?.setCurrentItem(2, true)
    }

    private fun initVerticalPager(view: View, isCreate: Boolean, showAnnouncement: Boolean) {
        view.verticalPager?.adapter = VerticalAdapter(childFragmentManager, lifecycle, showAnnouncement)
        view.verticalPager?.currentItem = if (isCreate && showAnnouncement) 1 else 0
        view.verticalPager?.offscreenPageLimit = 2
    }

}