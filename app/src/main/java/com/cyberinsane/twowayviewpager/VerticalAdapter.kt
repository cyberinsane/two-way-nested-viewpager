package com.cyberinsane.twowayviewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class VerticalAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            MainFragment.newInstance("Announcement")
        } else if (position == 1) {
            MainFragment.newInstance("Home")
        } else {
            MainFragment.newInstance("Menu")
        }
    }
}