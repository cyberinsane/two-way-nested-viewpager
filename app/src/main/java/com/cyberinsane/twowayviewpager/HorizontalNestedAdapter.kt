package com.cyberinsane.twowayviewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


class HorizontalNestedAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            AccountFragment.newInstance()
        } else if (position == 1) {
            VerticalPagerFragment.newInstance()
        } else {
            BagFragment.newInstance()
        }
    }
}