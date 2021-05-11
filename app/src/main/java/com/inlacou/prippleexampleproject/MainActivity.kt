package com.inlacou.prippleexampleproject

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import com.inlacou.pripple.RippleButton
import com.inlacou.pripple.batchEdit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        biggerCircularButton?.setOnClickListener {}
        rbutton_gradient?.setOnClickListener {  }
        rimageview?.setOnClickListener {  }

        //Add View programmatically, in an optimized way
        ll.addView(RippleButton(context = this).batchEdit {
            normalBackgroundColor = Color.RED
            rippleBackgroundColor = Color.WHITE
            cornerBottomLeft = 16f
            cornerTopRight = 16f
            cornerTopLeft = 0f
            cornerBottomRight = 0f
            strokeWidth = 4
            strokeColor = Color.BLACK
            text = "Programmatically added button"
            gravity = Gravity.CENTER
            setPadding(16)
            isClickable = true
            setOnClickListener {
                //Modify view programmatically, in an optimized way
                this.batchEdit {
                    normalBackgroundColor = Color.GREEN
                    rippleBackgroundColor = Color.BLUE
                    cornerBottomLeft = 16f
                    cornerTopRight = 16f
                    cornerTopLeft = 16f
                    cornerBottomRight = 16f
                }
            }
        })
    }
}
