package com.cyberinsane.twowayviewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cyberinsane.R
import com.cyberinsane.event.Navigate
import com.cyberinsane.event.NavigateEvent
import kotlinx.android.synthetic.main.fragment_menu.view.*
import org.greenrobot.eventbus.EventBus

class MenuFragment : Fragment() {

    companion object {
        fun newInstance() = MenuFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        view.navHome.setOnClickListener {
            EventBus.getDefault().post(NavigateEvent(Navigate.Home, true))
        }

        view.addToBag.setOnClickListener {
            EventBus.getDefault().post(NavigateEvent(Navigate.Bag, true))
        }

        return view
    }

}