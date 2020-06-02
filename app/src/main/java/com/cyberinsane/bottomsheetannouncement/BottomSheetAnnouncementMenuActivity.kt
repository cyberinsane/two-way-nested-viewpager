package com.cyberinsane.bottomsheetannouncement

import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.cyberinsane.R
import com.cyberinsane.event.Navigate
import com.cyberinsane.event.ToggleAnnouncement
import com.cyberinsane.twowayviewpager.AnnouncementFragment
import com.cyberinsane.twowayviewpager.VerticalPagerFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import kotlinx.android.synthetic.main.activity_bottom_sheet_menu.*
import kotlinx.android.synthetic.main.activity_double_pager.horizontalPager
import kotlinx.android.synthetic.main.activity_double_pager.navAccount
import kotlinx.android.synthetic.main.activity_double_pager.navBag
import kotlinx.android.synthetic.main.partial_bottom_menu.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import kotlin.math.absoluteValue


class BottomSheetAnnouncementMenuActivity : AppCompatActivity() {

    private var sheetBehavior: BottomSheetBehavior<*>? = null
    private var showBag: Boolean = false
    private var showAccount: Boolean = false

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

        addToBag.setOnClickListener {
            Handler().postDelayed({ navigate(Navigate.Bag, true) }, 500)
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

        //Get screen size
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels.toFloat()

        horizontalPager.adapter = HorizontalAnnouncementAdapter(supportFragmentManager, lifecycle)
        horizontalPager.offscreenPageLimit = 2
        horizontalPager.currentItem = 1

        horizontalPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            private val thresholdOffset = 0.5f
            private val thresholdOffsetPixels = 1
            private var scrollStarted = false
            private var checkDirection: Boolean = false


            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                if (checkDirection) {
                    if (thresholdOffset > positionOffset && positionOffsetPixels > thresholdOffsetPixels) {
                        println("going left")
                    } else {
                        println("going right")
                    }
                    checkDirection = false;
                }

                val pixelOffset = positionOffsetPixels.toFloat()

                if (pixelOffset > 0 && positionOffset > 0) {
                    if (position == 0) {
                        val translate = screenWidth - pixelOffset
                        bottomMenu.translationX = translate

                        val translateNav = translate / 2
                        if (translateNav.absoluteValue < screenWidth / 2.3) {
                            navAccount.translationX = translateNav
                        }

                        navBag.alpha = positionOffset

                    } else if (position == 1) {
                        val translate = 0 - pixelOffset
                        bottomMenu.translationX = translate

                        val translateNav = translate / 2
                        if (translateNav.absoluteValue < screenWidth / 2.3) {
                            navBag.translationX = translateNav
                        }

                        navAccount.alpha = 1 - positionOffset
                    }
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                //
                //                if (position == 1) {
                //                    sheetBehavior?.peekHeight =
                //                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50f, resources.displayMetrics).toInt()
                //                } else {
                //                    sheetBehavior?.peekHeight =
                //                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0f, resources.displayMetrics).toInt()
                //                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if (!scrollStarted && state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    scrollStarted = true;
                    checkDirection = true;
                } else {
                    scrollStarted = false;
                }
            }
        })

    }

    private fun initMenuDrawer() {
        sheetBehavior = BottomSheetBehavior.from(bottomMenu)


        val navMenuText = findViewById<TextView>(R.id.navMenu)
        val navHomeText = findViewById<TextView>(R.id.navHome)

        val bottomAccount = findViewById<View>(R.id.bottomNavAccount)
        val bottomBag = findViewById<View>(R.id.bottomNavBag)


        bottomAccount.setOnClickListener { navigate(Navigate.Account, true) }
        bottomBag.setOnClickListener { navigate(Navigate.Bag, true) }


        val maxMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics).toInt()
        setDrawerMargin(maxMargin)

        sheetBehavior?.addBottomSheetCallback(object : BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                val margin = (1 - slideOffset) * maxMargin
                setDrawerMargin(margin.toInt())
                blackout.alpha = slideOffset

                println("offset " + slideOffset)

                if (slideOffset > 0.8) {
                    bottomAccount.alpha = slideOffset
                    bottomBag.alpha = slideOffset

                    navMenuText.text = "HOME"

                } else {
                    bottomAccount.alpha = 0f
                    bottomBag.alpha = 0f

                    navMenuText.text = "MENU"
                }

                //                navHomeText.alpha = slideOffset
                //                navMenuText.alpha = (1 - slideOffset)
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        setDrawerMargin(maxMargin)
                        blackout.alpha = 0f

                        if (showBag) {
                            showBag = false
                            navigate(Navigate.Bag, true)
                        }

                        if (showAccount) {
                            showAccount = false
                            navigate(Navigate.Account, true)
                        }

                        //                        navMenuText.alpha = 0f
                        //                        navMenuText.text = "MENU"
                        //                        navMenuText.animate().alpha(1f).duration = 500
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        setDrawerMargin(0)
                        blackout.alpha = 1f

                        //                        navMenuText.alpha = 0f
                        //                        navMenuText.text = "HOME"
                        //                        navMenuText.animate().alpha(1f).duration = 500
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
        supportFragmentManager.fragments.firstOrNull { it is VerticalPagerFragment }?.let {
            (it as VerticalPagerFragment).navigateToAnnouncement()
        }
    }

    fun setDrawerMargin(margin: Int) {
        val layoutParams: FrameLayout.LayoutParams = card.layoutParams as FrameLayout.LayoutParams
        layoutParams.setMargins(margin, 0, margin, 0)
        card.layoutParams = layoutParams
    }

    private fun navigate(navigate: Navigate, animate: Boolean) {
        when (navigate) {
            Navigate.Account -> {
                if (sheetBehavior?.state == BottomSheetBehavior.STATE_EXPANDED) {
                    showAccount = true
                    sheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
                } else {
                    horizontalPager.setCurrentItem(0, animate)
                }
            }
            Navigate.Bag -> {
                if (sheetBehavior?.state == BottomSheetBehavior.STATE_EXPANDED) {
                    showBag = true
                    sheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
                } else {
                    horizontalPager.setCurrentItem(2, animate)
                }
            }
            Navigate.Home -> {
                horizontalPager.setCurrentItem(1, animate)
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
        if (horizontalPager.currentItem != 1) {
            navigate(Navigate.Home, true)
            return
        }

        super.onBackPressed()
    }

}