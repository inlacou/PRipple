package com.inlacou.pripple

import android.graphics.drawable.GradientDrawable

class RippleableMdl(
    var normalBackgroundColor: Int? = null,
    var rippleBackgroundColor: Int? = null,
    var gradientColors: List<Int>? = null,
    var gradientOrientation: GradientDrawable.Orientation? = null,
    var gradientType: Rippleable.GradientTypes? = null,
    var gradientRadius: Float? = null,
    var gradientCenterX: Float? = null,
    var gradientCenterY: Float? = null,
    var corners: Float? = null,
    var cornerTopLeft: Float? = null,
    var cornerTopRight: Float? = null,
    var cornerBottomLeft: Float? = null,
    var cornerBottomRight: Float? = null,
    var strokeColor: Int? = null,
    var strokeWidth: Int? = null,
)