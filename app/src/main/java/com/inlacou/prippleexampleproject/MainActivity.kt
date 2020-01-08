package com.inlacou.prippleexampleproject

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val animalNames = ArrayList<String>()
        animalNames.add("Horse")
        animalNames.add("Cow")
        animalNames.add("Camel")
        animalNames.add("Sheep")
        animalNames.add("Goat")
        animalNames.add("Elephant")
        animalNames.add("Dog")
        animalNames.add("Cat")

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = MyRecyclerViewAdapter(this, animalNames)
        biggerCircularButton?.setOnClickListener {}
        rbutton_gradient?.setOnClickListener {  }
    }

    private fun getColorCompat(int: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) resources.getColor(int, null)
        else resources.getColor(int)
    }

}
