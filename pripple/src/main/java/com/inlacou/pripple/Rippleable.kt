package com.inlacou.pripple

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.*
import android.os.Build
import android.graphics.drawable.Drawable
import android.util.AttributeSet

interface Rippleable: BatchEditable {

	val viewContext: Context

	/**
	 * Intended for internal use. Use `batch {}` instead.
	 * Otherwise, use at your own risk.
	 * This method as true will block any rippleable UI updates.
	 */
	override var batchEditing: Boolean

	var forceClipChildren: Boolean
	var clickableOverChildren: Boolean
	var normalBackgroundColor: Int?
	var rippleBackgroundColor: Int?
	var gradientColors: List<Int>?
	var gradientOrientation: GradientDrawable.Orientation?
	var gradientType: GradientTypes?
	var gradientRadius: Float?
	var gradientCenterX: Float?
	var gradientCenterY: Float?
	var corners: Float?
	var cornerTopLeft: Float
	var cornerTopRight: Float
	var cornerBottomLeft: Float
	var cornerBottomRight: Float
	var strokeColor: Int?
	var strokeWidth: Int

	fun applyMdl(mdl: RippleableMdl) {
		batchEdit {
			normalBackgroundColor = mdl.normalBackgroundColor
			rippleBackgroundColor = mdl.rippleBackgroundColor
			gradientColors = mdl.gradientColors
			gradientOrientation = mdl.gradientOrientation
			gradientType = mdl.gradientType
			gradientRadius = mdl.gradientRadius
			gradientCenterX = mdl.gradientCenterX
			gradientCenterY = mdl.gradientCenterY
			corners = mdl.corners
			mdl.cornerTopLeft?.let { cornerTopLeft = it }
			mdl.cornerTopRight?.let { cornerTopRight = it }
			mdl.cornerBottomLeft?.let { cornerBottomLeft = it }
			mdl.cornerBottomRight?.let { cornerBottomRight = it }
			strokeColor = mdl.strokeColor
			mdl.strokeWidth?.let { strokeWidth = it }
		}
	}

	fun readAttrs(attrs: AttributeSet) {
		startBatch()
		val ta = viewContext.obtainStyledAttributes(attrs, R.styleable.Rippleable, 0, 0)
		try {
			if (ta.hasValue(R.styleable.Rippleable_forceClipChildren)) {
				forceClipChildren = ta.getBoolean(R.styleable.Rippleable_forceClipChildren, false)
			}
			if (ta.hasValue(R.styleable.Rippleable_clickableOverChildren)) {
				clickableOverChildren = ta.getBoolean(R.styleable.Rippleable_clickableOverChildren, true)
			}
			if (ta.hasValue(R.styleable.Rippleable_backgroundNormal)) {
				normalBackgroundColor = ta.getColor(R.styleable.Rippleable_backgroundNormal, -1)
			}
			if (ta.hasValue(R.styleable.Rippleable_backgroundRipple)) {
				rippleBackgroundColor = ta.getColor(R.styleable.Rippleable_backgroundRipple, -1)
			}
			if (ta.hasValue(R.styleable.Rippleable_gradientColors)) {
				gradientColors = ta.resources.getIntArray(ta.getResourceId(R.styleable.Rippleable_gradientColors, -1)).toList()
			}
			if (ta.hasValue(R.styleable.Rippleable_gradientOrientation)) {
				gradientOrientation = GradientDrawable.Orientation.values()[ta.getInt(R.styleable.Rippleable_gradientOrientation, 0)]
			}
			if (ta.hasValue(R.styleable.Rippleable_gradientType)) {
				gradientType = GradientTypes.values()[ta.getInt(R.styleable.Rippleable_gradientType, 0)]
			}
			if (ta.hasValue(R.styleable.Rippleable_gradientRadius)) {
				gradientRadius = ta.getFloat(R.styleable.Rippleable_gradientRadius, -1f).let {
					if(it==-1f) null
					else it
				}
			}
			if (ta.hasValue(R.styleable.Rippleable_gradientCenterX)) {
				gradientCenterX = ta.getFloat(R.styleable.Rippleable_gradientCenterX, -1f).let {
					if(it==-1f) null
					else it
				}
			}
			if (ta.hasValue(R.styleable.Rippleable_gradientCenterY)) {
				gradientCenterY = ta.getFloat(R.styleable.Rippleable_gradientCenterY, -1f).let {
					if(it==-1f) null
					else it
				}
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
			dropBatch()
		}
		setDraw(false)
		//setBackground()
	}

	override fun applyBatch() {
		batchEditing = false
		setBackground()
	}
	fun setDraw(b: Boolean)
	fun setViewBackground(drawable: Drawable)

	/**
	 * Extreme case. Use at your own risk.
	 */
	fun setBackground(gradientDrawable: GradientDrawable) {
		normalBackgroundColor?.let { normalColor ->
			setViewBackground(getRippleDrawable(gradientDrawable, normalColor, rippleBackgroundColor,
				floatArrayOf(corners ?: cornerTopLeft, corners ?: cornerTopLeft, corners ?: cornerTopRight, corners ?: cornerTopRight,
					corners ?: cornerBottomRight, corners ?: cornerBottomRight, corners ?: cornerBottomLeft, corners ?: cornerBottomLeft),
				strokeColor, strokeWidth))
		}
	}

	fun setBackground() {
		if(batchEditing) return
		normalBackgroundColor.let { normalColor ->
			gradientColors.let { gradientColors ->
				gradientOrientation.let { gradientOrientation ->
					if(gradientColors!=null && gradientOrientation!=null && gradientColors.isNotEmpty()) {
						setViewBackground(getRippleDrawable(gradientColors, gradientOrientation, rippleBackgroundColor,
							floatArrayOf(corners ?: cornerTopLeft, corners ?: cornerTopLeft, corners ?: cornerTopRight, corners ?: cornerTopRight,
								corners ?: cornerBottomRight, corners ?: cornerBottomRight, corners ?: cornerBottomLeft, corners ?: cornerBottomLeft),
							strokeColor, strokeWidth))
					}else if(normalColor!=null) {
						setViewBackground(getRippleDrawable(normalColor, rippleBackgroundColor,
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
		return buildRippleDrawable(getGradientColorDrawable(colors, orientation, gradientType, gradientRadius, gradientCenterX, gradientCenterY, corners, strokeColor, strokeWidth), pressedColor ?: colors.first(), corners, strokeColor, strokeWidth)
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

	private fun getGradientColorDrawable(colors: List<Int>, orientation: GradientDrawable.Orientation, gradientType: GradientTypes?, gradientRadius: Float?,
										 gradientCenterX: Float?, gradientCenterY: Float?,
										 corners: FloatArray, strokeColor: Int?, strokeWidth: Int): Drawable {
		val backgroundDrawable = GradientDrawable(orientation, colors.toIntArray())
		if(gradientType!=null) backgroundDrawable.gradientType = gradientType.ordinal
		if(gradientRadius!=null) backgroundDrawable.gradientRadius = gradientRadius
		if(gradientCenterX!=null && gradientCenterY!=null) backgroundDrawable.setGradientCenter(gradientCenterX, gradientCenterY)

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

	enum class GradientTypes { LINEAR_GRADIENT, RADIAL_GRADIENT, SWEEP_GRADIENT }
}