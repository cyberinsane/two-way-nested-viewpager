package com.cyberinsane.twowayviewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cyberinsane.R
import com.cyberinsane.event.Navigate
import com.cyberinsane.event.NavigateEvent
import kotlinx.android.synthetic.main.fragment_announcement.view.*
import org.greenrobot.eventbus.EventBus

class AnnouncementFragment : Fragment() {

    companion object {

        private const val EXTRA_EXIT = "EXIT"

        fun newInstance(showExit: Boolean): AnnouncementFragment {
            val bundle = Bundle()
            bundle.putBoolean(EXTRA_EXIT, showExit)
            val fragment = AnnouncementFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_announcement, container, false)

        view.navOrder.setOnClickListener {
            EventBus.getDefault().post(NavigateEvent(Navigate.Home, true))
        }

        if (arguments?.getBoolean(EXTRA_EXIT) == true) {
            view.close.visibility = View.VISIBLE
            view.navOrder.visibility = View.GONE
        } else {
            view.close.visibility = View.GONE
            view.navOrder.visibility = View.VISIBLE
        }

        view.close.setOnClickListener {
            getActivity()?.onBackPressed()
        }

        return view
    }

}