package com.inlacou.pripple

import android.content.res.Resources
import android.util.Log

internal fun Int.dpToPx() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun <T: Rippleable> T.batch(block: T.() -> Unit): T {
    batch = true
    block()
    batch = false
    setBackground()
    return this
}