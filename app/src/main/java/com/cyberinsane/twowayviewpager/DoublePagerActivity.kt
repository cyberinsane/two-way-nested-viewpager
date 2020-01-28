package com.cyberinsane.twowayviewpager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cyberinsane.R
import com.cyberinsane.event.Navigate
import com.cyberinsane.event.NavigateEvent
import com.cyberinsane.event.ToggleAnnouncement
import kotlinx.android.synthetic.main.activity_double_pager.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class DoublePagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_double_pager)
        horizontalPager.adapter = HorizontalNestedAdapter(supportFragmentManager, lifecycle)
        horizontalPager.offscreenPageLimit = 2
        horizontalPager.currentItem = 1

        navAccount.setOnClickListener {
            navigate(Navigate.Account, true)
        }

        navBag.setOnClickListener {
            navigate(Navigate.Bag, true)
        }

    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onNavigate(event: NavigateEvent) {
        navigate(event.navigate, event.animate)
    }

    @Subscribe
    fun onToggleAnnouncement(event: ToggleAnnouncement) {
        supportFragmentManager.fragments.firstOrNull { it is VerticalPagerFragment }?.let {
            (it as VerticalPagerFragment).toggleAnnouncement()
        }
    }

    private fun navigate(navigate: Navigate, animate: Boolean) {
        when (navigate) {
            Navigate.Account -> {
                horizontalPager.setCurrentItem(0, animate)
            }
            Navigate.Bag -> {
                horizontalPager.setCurrentItem(2, animate)
            }
            Navigate.Menu -> {
                supportFragmentManager.fragments.firstOrNull { it is VerticalPagerFragment }?.let {
                    (it as VerticalPagerFragment).navigateToMenu()
                }
            }
            Navigate.Home -> {
                horizontalPager.setCurrentItem(1, animate)
                supportFragmentManager.fragments.firstOrNull { it is VerticalPagerFragment }?.let {
                    (it as VerticalPagerFragment).navigateToHome()
                }
            }
            else -> {
                print("no-op")
            }
        }
    }

    private fun makeFragmentName(viewId: Int, id: Long): String? {
        return "android:switcher:$viewId:$id"
    }

}
