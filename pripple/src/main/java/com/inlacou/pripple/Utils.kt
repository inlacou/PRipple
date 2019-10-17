package com.inlacou.pripple

import android.content.res.Resources

internal fun Int.dpToPx() = (this * Resources.getSystem().displayMetrics.density).toInt()