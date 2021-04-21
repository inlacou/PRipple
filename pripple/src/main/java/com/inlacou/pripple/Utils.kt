package com.inlacou.pripple

import android.content.res.Resources
import android.util.Log

internal fun Int.dpToPx() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun <T: Rippleable> T.batch(block: T.() -> Unit): T {
    Log.d("batch", "before")
    batch = true
    block()
    batch = false
    Log.d("batch", "after")
    setBackground()
    return this
}