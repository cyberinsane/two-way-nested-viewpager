package com.cyberinsane.twowayviewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class VerticalAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val showAnnouncement: Boolean) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return if (showAnnouncement) 3 else 2
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 0 && showAnnouncement) {
            return AnnouncementFragment.newInstance(false)
        } else if ((position == 0 && !showAnnouncement) || (position == 1 && showAnnouncement)) {
            return HomeFragment.newInstance()
        } else {
            return MenuFragment.newInstance()
        }
    }
}