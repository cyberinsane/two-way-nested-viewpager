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
        view.verticalPager.adapter = VerticalAdapter(childFragmentManager, lifecycle)
        view.verticalPager.currentItem = 1
        view.verticalPager.offscreenPageLimit = 2
        return view
    }

}