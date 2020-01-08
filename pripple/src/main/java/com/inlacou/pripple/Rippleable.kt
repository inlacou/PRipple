package com.inlacou.pripple

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.*
import android.os.Build
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.core.view.ViewCompat.setBackground

interface Rippleable {

	val viewContext: Context

	var normalColor: Int?
	var rippleColor: Int?
	var gradientColors: List<Int>?
	var gradientOrientation: GradientDrawable.Orientation?
	var corners: Float?
	var cornerTopLeft: Float
	var cornerTopRight: Float
	var cornerBottomLeft: Float
	var cornerBottomRight: Float
	var strokeColor: Int?
	var strokeWidth: Int

	fun readAttrs(attrs: AttributeSet) {
		val ta = viewContext.obtainStyledAttributes(attrs, R.styleable.Rippleable, 0, 0)
		try {
			if (ta.hasValue(R.styleable.Rippleable_normal)) {
				normalColor = ta.getColor(R.styleable.Rippleable_normal, -1)
			}
			if (ta.hasValue(R.styleable.Rippleable_ripple)) {
				rippleColor = ta.getColor(R.styleable.Rippleable_ripple, -1)
			}
			if (ta.hasValue(R.styleable.Rippleable_gradientColors)) {
				gradientColors = ta.resources.getIntArray(ta.getResourceId(R.styleable.Rippleable_gradientColors, -1)).toList()
			}
			if (ta.hasValue(R.styleable.Rippleable_gradientOrientation)) {
				gradientOrientation = GradientDrawable.Orientation.values()[ta.getInt(R.styleable.Rippleable_gradientOrientation, 0)]
			}
			if (ta.hasValue(R.styleable.Rippleable_corners)) {
				val aux = ta.getDimension(R.styleable.Rippleable_corners, -10f)
				if(aux > -1) corners = aux
			}
			if (ta.hasValue(R.styleable.Rippleable_cornerTopLeft)) {
				cornerTopLeft = ta.getDimension(R.styleable.Rippleable_cornerTopLeft, 0f)
			}
			if (ta.hasValue(R.styleable.Rippleable_cornerTopRight)) {
				cornerTopRight = ta.getDimension(R.styleable.Rippleable_cornerTopRight, 0f)
			}
			if (ta.hasValue(R.styleable.Rippleable_cornerBottomLeft)) {
				cornerBottomLeft = ta.getDimension(R.styleable.Rippleable_cornerBottomLeft, 0f)
			}
			if (ta.hasValue(R.styleable.Rippleable_cornerBottomRight)) {
				cornerBottomRight = ta.getDimension(R.styleable.Rippleable_cornerBottomRight, 0f)
			}
			if (ta.hasValue(R.styleable.Rippleable_strokeColor)) {
				strokeColor = ta.getColor(R.styleable.Rippleable_strokeColor, -1)
			}
			if (ta.hasValue(R.styleable.Rippleable_strokeWidth)) {
				strokeWidth = ta.getDimension(R.styleable.Rippleable_strokeWidth, 4.dpToPx().toFloat()).toInt()
			}
		} finally {
			ta.recycle()
		}
		setBackground()
	}

	fun setViewBackground(drawable: Drawable)

	/**
	 * Extreme case. Use at your own risk.
	 */
	fun setBackground(gradientDrawable: GradientDrawable) {
		normalColor?.let { normalColor ->
			setViewBackground(getRippleDrawable(gradientDrawable, normalColor, rippleColor,
				floatArrayOf(corners ?: cornerTopLeft, corners ?: cornerTopLeft, corners ?: cornerTopRight, corners ?: cornerTopRight,
					corners ?: cornerBottomRight, corners ?: cornerBottomRight, corners ?: cornerBottomLeft, corners ?: cornerBottomLeft),
				strokeColor, strokeWidth))
		}
	}

	fun setBackground() {
		normalColor.let { normalColor ->
			gradientColors.let { gradientColors ->
				gradientOrientation.let { gradientOrientation ->
					if(gradientColors!=null && gradientOrientation!=null && gradientColors.isNotEmpty()) {
						setViewBackground(getRippleDrawable(gradientColors, gradientOrientation, rippleColor,
							floatArrayOf(corners ?: cornerTopLeft, corners ?: cornerTopLeft, corners ?: cornerTopRight, corners ?: cornerTopRight,
								corners ?: cornerBottomRight, corners ?: cornerBottomRight, corners ?: cornerBottomLeft, corners ?: cornerBottomLeft),
							strokeColor, strokeWidth))
					}else if(normalColor!=null) {
						setViewBackground(getRippleDrawable(normalColor, rippleColor,
							floatArrayOf(corners ?: cornerTopLeft, corners ?: cornerTopLeft, corners ?: cornerTopRight, corners ?: cornerTopRight,
								corners ?: cornerBottomRight, corners ?: cornerBottomRight, corners ?: cornerBottomLeft, corners ?: cornerBottomLeft),
							strokeColor, strokeWidth))
					}
				}
			}
		}
	}

	fun getRippleDrawable(normalColor: Int, pressedColor: Int? = null, corners: FloatArray, strokeColor: Int?, strokeWidth: Int): Drawable {
		return buildRippleDrawable(getPlainColorDrawable(normalColor, corners, strokeColor, strokeWidth), pressedColor ?: normalColor, corners, strokeColor, strokeWidth)
	}

	fun getRippleDrawable(colors: List<Int>, orientation: GradientDrawable.Orientation, pressedColor: Int? = null, corners: FloatArray, strokeColor: Int?, strokeWidth: Int): Drawable {
		return buildRippleDrawable(getGradientColorDrawable(colors, orientation, corners, strokeColor, strokeWidth), pressedColor ?: colors.first(), corners, strokeColor, strokeWidth)
	}

	fun getRippleDrawable(gradientDrawable: GradientDrawable, normalColor: Int, pressedColor: Int? = null, corners: FloatArray, strokeColor: Int?, strokeWidth: Int): Drawable {
		return buildRippleDrawable(gradientDrawable, pressedColor ?: normalColor, corners, strokeColor, strokeWidth)
	}

	fun buildRippleDrawable(drawable: Drawable, pressedColor: Int, corners: FloatArray, strokeColor: Int?, strokeWidth: Int): Drawable {
		return if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
			RippleDrawable(getPressedColorSelector(pressedColor), drawable, null)
		} else {
			StateListDrawable().apply {
				addState(intArrayOf(android.R.attr.state_pressed), drawable)
				addState(intArrayOf(android.R.attr.state_focused), drawable)
				addState(intArrayOf(), drawable)
			}
		}
	}

	private fun getPressedColorSelector(pressedColor: Int): ColorStateList {
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
					pressedColor)
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
		//TODO https@//stackoverflow.com/questions/8481322/create-a-radial-gradient-programmatically

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