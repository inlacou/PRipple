package com.inlacou.pripple

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.*
import android.os.Build
import android.graphics.drawable.Drawable
import android.util.Log

interface Rippleable {

	fun getPressedColorRippleDrawable(normalColor: Int, pressedColor: Int? = null, corners: FloatArray, strokeColor: Int?, strokeWidth: Int): Drawable {
		val pressedColor = pressedColor ?: normalColor
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

	private fun getColorDrawableFromColor(color: Int, corners: FloatArray, strokeColor: Int?, strokeWidth: Int): Drawable {
		val backgroundDrawable = GradientDrawable()
		backgroundDrawable.setColor(color)
		/*
		 * Specify radii for each of the 4 corners. For each corner, the array contains 2 values, [X_radius, Y_radius].
		 * The corners are ordered top-left, top-right, bottom-right, bottom-left.
		 * This property is honored only when the shape is of type RECTANGLE.
		 */
		backgroundDrawable.cornerRadii = corners

		strokeColor.let {
			if(it!=null) backgroundDrawable.setStroke(strokeWidth, it)
			else backgroundDrawable.setStroke(0, color)
		}

		return backgroundDrawable
	}

	fun Int.dpToPx() = (this * Resources.getSystem().displayMetrics.density).toInt()
}