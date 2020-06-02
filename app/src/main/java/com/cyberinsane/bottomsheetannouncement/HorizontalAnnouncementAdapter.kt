package com.cyberinsane.bottomsheetannouncement

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cyberinsane.twowayviewpager.AccountFragment
import com.cyberinsane.twowayviewpager.BagFragment
import com.cyberinsane.twowayviewpager.HomeFragment
import com.cyberinsane.twowayviewpager.VerticalPagerFragment


class HorizontalAnnouncementAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            AccountFragment.newInstance()
        } else if (position == 1) {
            VerticalPagerFragment.newInstance(true)
        } else {
            BagFragment.newInstance()
        }
    }
}