package com.inlacou.pripple

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.*
import android.os.Build
import android.graphics.drawable.Drawable

interface Rippleable {

	fun getRippleDrawable(normalColor: Int, pressedColor: Int? = null, corners: FloatArray, strokeColor: Int?, strokeWidth: Int): Drawable {
		return buildRippleDrawable(getPlainColorDrawable(normalColor, corners, strokeColor, strokeWidth), normalColor, pressedColor ?: normalColor, corners, strokeColor, strokeWidth)
	}

	fun getRippleDrawable(colors: List<Int>, orientation: GradientDrawable.Orientation, pressedColor: Int? = null, corners: FloatArray, strokeColor: Int?, strokeWidth: Int): Drawable {
		return buildRippleDrawable(getGradientColorDrawable(colors, orientation, corners, strokeColor, strokeWidth), colors.first(), pressedColor ?: colors.first(), corners, strokeColor, strokeWidth)
	}

	fun getRippleDrawable(gradientDrawable: GradientDrawable, normalColor: Int, pressedColor: Int? = null, corners: FloatArray, strokeColor: Int?, strokeWidth: Int): Drawable {
		return buildRippleDrawable(gradientDrawable, normalColor, pressedColor ?: normalColor, corners, strokeColor, strokeWidth)
	}

	fun buildRippleDrawable(drawable: Drawable, normalColor: Int, pressedColor: Int, corners: FloatArray, strokeColor: Int?, strokeWidth: Int): Drawable {
		return if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
			RippleDrawable(getPressedColorSelector(normalColor, pressedColor), drawable, null)
		} else {
			StateListDrawable().apply {
				addState(intArrayOf(android.R.attr.state_pressed), drawable)
				addState(intArrayOf(android.R.attr.state_focused), drawable)
				addState(intArrayOf(), drawable)
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

	private fun getPlainColorDrawable(color: Int, corners: FloatArray, strokeColor: Int?, strokeWidth: Int): Drawable {
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

	private fun getGradientColorDrawable(colors: List<Int>, orientation: GradientDrawable.Orientation, corners: FloatArray, strokeColor: Int?, strokeWidth: Int): Drawable {
		val backgroundDrawable = GradientDrawable(orientation, colors.toIntArray())
		/*
		 * Specify radii for each of the 4 corners. For each corner, the array contains 2 values, [X_radius, Y_radius].
		 * The corners are ordered top-left, top-right, bottom-right, bottom-left.
		 * This property is honored only when the shape is of type RECTANGLE.
		 */
		backgroundDrawable.cornerRadii = corners

		strokeColor.let {
			if(it!=null) backgroundDrawable.setStroke(strokeWidth, it)
			else backgroundDrawable.setStroke(0, colors.first())
		}

		return backgroundDrawable
	}
}