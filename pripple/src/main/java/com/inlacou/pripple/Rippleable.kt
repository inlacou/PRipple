package com.inlacou.pripple

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.*
import android.os.Build
import android.graphics.drawable.Drawable

interface Rippleable {

	fun getPressedColorRippleDrawable(normalColor: Int, pressedColor: Int, corners: Float, strokeColor: Int?, strokeWidth: Int): Drawable {
		return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			RippleDrawable(
					getPressedColorSelector(normalColor, pressedColor),
					getColorDrawableFromColor(normalColor, corners, strokeColor, strokeWidth),
					null)
		} else {
			StateListDrawable().apply {
				addState(
					intArrayOf(android.R.attr.state_pressed),
					getColorDrawableFromColor(pressedColor, corners, strokeColor, strokeWidth))
				addState(
					intArrayOf(android.R.attr.state_focused),
					getColorDrawableFromColor(pressedColor, corners, strokeColor, strokeWidth))
				addState(
					intArrayOf(),
					getColorDrawableFromColor(normalColor, corners, strokeColor, strokeWidth))
			}
		}
	}

	private fun getPressedColorSelector(normalColor: Int, pressedColor: Int): ColorStateList {
		return ColorStateList(
				arrayOf(
						intArrayOf(android.R.attr.state_pressed),
						intArrayOf(android.R.attr.state_focused),
						intArrayOf(android.R.attr.state_activated),
						intArrayOf()),
				intArrayOf(
						pressedColor,
						pressedColor,
						pressedColor,
						normalColor)
		)
	}

	private fun getColorDrawableFromColor(color: Int, corners: Float, strokeColor: Int?, strokeWidth: Int): Drawable {
		val backgroundDrawable = GradientDrawable()
		backgroundDrawable.setColor(color)
		backgroundDrawable.cornerRadius = corners

		strokeColor?.let {
			backgroundDrawable.cornerRadius = corners
			backgroundDrawable.setStroke(strokeWidth, strokeColor)
		}

		return backgroundDrawable
	}

	fun Int.dpToPx() = (this * Resources.getSystem().displayMetrics.density).toInt()
}