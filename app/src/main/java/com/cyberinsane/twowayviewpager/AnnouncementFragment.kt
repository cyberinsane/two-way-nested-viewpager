package com.cyberinsane.twowayviewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cyberinsane.twowayviewpager.event.Navigate
import com.cyberinsane.twowayviewpager.event.NavigateEvent
import kotlinx.android.synthetic.main.fragment_announcement.view.*
import org.greenrobot.eventbus.EventBus

class AnnouncementFragment : Fragment() {

    companion object {
        fun newInstance() = AnnouncementFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_announcement, container, false)

        view.navOrder.setOnClickListener {
            EventBus.getDefault().post(NavigateEvent(Navigate.Home, true))
        }

        return view
    }

}