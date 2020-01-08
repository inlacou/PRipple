package com.inlacou.prippleexampleproject

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        biggerCircularButton?.setOnClickListener {}
        rbutton_gradient?.setOnClickListener {  }
    }

    private fun getColorCompat(int: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) resources.getColor(int, null)
        else resources.getColor(int)
    }

}
