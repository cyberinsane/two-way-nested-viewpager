package com.cyberinsane.twowayviewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cyberinsane.twowayviewpager.event.Navigate
import com.cyberinsane.twowayviewpager.event.NavigateEvent
import com.cyberinsane.twowayviewpager.event.ToggleAnnouncement
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.greenrobot.eventbus.EventBus

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        view.navMenu.setOnClickListener {
            EventBus.getDefault().post(NavigateEvent(Navigate.Menu, true))
        }

        view.toggleAnnouncement.setOnClickListener {
            EventBus.getDefault().post(ToggleAnnouncement())
        }

        return view
    }

}