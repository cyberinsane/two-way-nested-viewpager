package com.cyberinsane

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cyberinsane.bottomsheet.BottomSheetMenuActivity
import com.cyberinsane.twowayviewpager.DoublePagerActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        doublePager.setOnClickListener {
            startActivity(Intent(this, DoublePagerActivity::class.java))
        }

        bottomSheet.setOnClickListener {
            startActivity(Intent(this, BottomSheetMenuActivity::class.java))
        }

    }

}