package com.inlacou.prippleexampleproject

import android.graphics.drawable.GradientDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        biggerCircularButton?.setOnClickListener {
            rbutton_gradient.setBackground(listOf(getColorCompat(R.color.basic_white), getColorCompat(R.color.basic_blue)), GradientDrawable.Orientation.TOP_BOTTOM)
        }
        rbutton_gradient?.setOnClickListener {  }
    }

    private fun getColorCompat(int: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) resources.getColor(int, null)
        else resources.getColor(int)
    }

}
