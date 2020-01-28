package com.cyberinsane.bottomsheet

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.cyberinsane.R
import com.cyberinsane.event.Navigate
import com.cyberinsane.event.ToggleAnnouncement
import com.cyberinsane.twowayviewpager.AnnouncementFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import kotlinx.android.synthetic.main.activity_bottom_sheet_menu.*
import kotlinx.android.synthetic.main.activity_double_pager.horizontalPager
import kotlinx.android.synthetic.main.activity_double_pager.navAccount
import kotlinx.android.synthetic.main.activity_double_pager.navBag
import kotlinx.android.synthetic.main.partial_bottom_menu.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class BottomSheetMenuActivity : AppCompatActivity() {

    private var sheetBehavior: BottomSheetBehavior<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet_menu)

        initHorizontalPager()

        initMenuDrawer()

        navAccount.setOnClickListener {
            navigate(Navigate.Account, true)
        }

        navBag.setOnClickListener {
            navigate(Navigate.Bag, true)
        }

        drawer.setOnClickListener {
            if (sheetBehavior?.state == BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
            } else if (sheetBehavior?.state == BottomSheetBehavior.STATE_COLLAPSED) {
                sheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

    }

    private fun initHorizontalPager() {
        horizontalPager.adapter = HorizontalAdapter(supportFragmentManager, lifecycle)
        horizontalPager.offscreenPageLimit = 2
        horizontalPager.currentItem = 1

        horizontalPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                println("Position: " + position)
                println("positionOffset: " + positionOffset)
                println("positionOffsetPixels: " + positionOffsetPixels)

            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                println("onPageSelected: " + position)
            }
        })

    }

    private fun initMenuDrawer() {
        sheetBehavior = BottomSheetBehavior.from(bottomMenu)


        val maxMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics).toInt()
        setDrawerMargin(maxMargin)

        sheetBehavior?.addBottomSheetCallback(object : BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                val margin = (1 - slideOffset) * maxMargin
                setDrawerMargin(margin.toInt())
                blackout.alpha = slideOffset
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        setDrawerMargin(maxMargin)
                        blackout.alpha = 0f
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        setDrawerMargin(0)
                        blackout.alpha = 1f
                    }
                    else -> {
                        // no-op
                    }
                }
            }
        })
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
    fun onToggleAnnouncement(event: ToggleAnnouncement) {
        supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_down, R.anim.slide_up)
            .add(android.R.id.content, AnnouncementFragment.newInstance(true), "Announcement").commit()
    }

    fun setDrawerMargin(margin: Int) {
        val layoutParams: FrameLayout.LayoutParams = card.layoutParams as FrameLayout.LayoutParams
        layoutParams.setMargins(margin, 0, margin, 0)
        card.layoutParams = layoutParams
    }

    private fun navigate(navigate: Navigate, animate: Boolean) {
        when (navigate) {
            Navigate.Account -> {
                horizontalPager.setCurrentItem(0, animate)
            }
            Navigate.Bag -> {
                horizontalPager.setCurrentItem(2, animate)
            }
        }
    }

    override fun onBackPressed() {
        if (sheetBehavior?.state == BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
            return
        }
        if (supportFragmentManager.fragments.firstOrNull { it is AnnouncementFragment } != null) {
            supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_down, R.anim.slide_up)
                .remove(supportFragmentManager.fragments.first { it is AnnouncementFragment }).commit()
            return
        }
        super.onBackPressed()
    }

}