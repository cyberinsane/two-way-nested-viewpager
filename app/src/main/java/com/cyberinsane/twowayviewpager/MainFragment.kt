package com.cyberinsane.twowayviewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cyberinsane.R
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {

    companion object {

        private const val EXTRA_TITLE = "title"

        fun newInstance(title: String): Fragment {
            val bundle = Bundle()
            bundle.putString(EXTRA_TITLE, title)
            val fragment = MainFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        view.message.text = arguments?.getString(EXTRA_TITLE)
        return view
    }

}
