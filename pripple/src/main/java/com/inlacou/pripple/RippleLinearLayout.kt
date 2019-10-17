package com.inlacou.pripple

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.LinearLayout

open class RippleLinearLayout: LinearLayout, Rippleable {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	var normalColor: Int? = null
		set(value) {
			if(value!=null) {
				field = value
				setBackground()
			}
		}
	var rippleColor: Int? = null
		set(value) {
			if(value!=null) {
				field = value
				setBackground()
			}
		}
	var gradientColors: List<Int>? = null
		set(value) {
			if(value!=null) {
				field = value
				setBackground()
			}
		}
	var gradientOrientation: GradientDrawable.Orientation? = null
		set(value) {
			if(value!=null) {
				field = value
				setBackground()
			}
		}
	/**
	 * In px
	 */
	var corners: Float? = null
		set(value) {
			field = value
			setBackground()
		}
	/**
	 * In px
	 */
	var cornerTopLeft: Float = 0f
		set(value) {
			field = value
			setBackground()
		}
	/**
	 * In px
	 */
	var cornerTopRight: Float = 0f
		set(value) {
			field = value
			setBackground()
		}
	/**
	 * In px
	 */
	var cornerBottomLeft: Float = 0f
		set(value) {
			field = value
			setBackground()
		}
	/**
	 * In px
	 */
	var cornerBottomRight: Float = 0f
		set(value) {
			field = value
			setBackground()
		}
	var strokeColor: Int? = null
		set(value) {
			if(value!=null) {
				field = value
				setBackground()
			}
		}
	/**
	 * In px
	 */
	var strokeWidth: Int = 2.dpToPx()
		set(value) {
			field = value
			setBackground()
		}

	/**
	 * Extreme case. Use at your own risk.
	 */
	fun setBackground(gradientDrawable: GradientDrawable) {
		normalColor?.let { normalColor ->
			background = getRippleDrawable(gradientDrawable, normalColor, rippleColor,
				floatArrayOf(corners ?: cornerTopLeft, corners ?: cornerTopLeft, corners ?: cornerTopRight, corners ?: cornerTopRight,
					corners ?: cornerBottomRight, corners ?: cornerBottomRight, corners ?: cornerBottomLeft, corners ?: cornerBottomLeft),
				strokeColor, strokeWidth)
		}
	}

	private fun setBackground() {
		normalColor.let { normalColor ->
			gradientColors.let { gradientColors ->
				gradientOrientation.let { gradientOrientation ->
					if(gradientColors!=null && gradientOrientation!=null && gradientColors.isNotEmpty()) {
						background = getRippleDrawable(gradientColors, gradientOrientation, rippleColor,
							floatArrayOf(corners ?: cornerTopLeft, corners ?: cornerTopLeft, corners ?: cornerTopRight, corners ?: cornerTopRight,
								corners ?: cornerBottomRight, corners ?: cornerBottomRight, corners ?: cornerBottomLeft, corners ?: cornerBottomLeft),
							strokeColor, strokeWidth)
					}else if(normalColor!=null) {
						background = getRippleDrawable(normalColor, rippleColor,
							floatArrayOf(corners ?: cornerTopLeft, corners ?: cornerTopLeft, corners ?: cornerTopRight, corners ?: cornerTopRight,
								corners ?: cornerBottomRight, corners ?: cornerBottomRight, corners ?: cornerBottomLeft, corners ?: cornerBottomLeft),
							strokeColor, strokeWidth)
					}
				}
			}
		}
		setClickableOverChilds()
	}

	fun setClickableOverChilds(){
		(0 .. childCount)
				.map { getChildAt(it) }
				.filter { it!=null }
				.forEach { it.isClickable = false }
	}

	override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
		super.onLayout(changed, left, top, right, bottom)
		setBackground()
	}

	protected open fun readAttrs(attrs: AttributeSet) {
		val ta = context.obtainStyledAttributes(attrs, R.styleable.RippleLinearLayout, 0, 0)
		try {
			if (ta.hasValue(R.styleable.RippleLinearLayout_normal)) {
				normalColor = ta.getColor(R.styleable.RippleLinearLayout_normal, -1)
			}
			if (ta.hasValue(R.styleable.RippleLinearLayout_ripple)) {
				rippleColor = ta.getColor(R.styleable.RippleLinearLayout_ripple, -1)
			}
			if (ta.hasValue(R.styleable.RippleLinearLayout_gradientColors)) {
				gradientColors = ta.resources.getIntArray(ta.getResourceId(R.styleable.RippleLinearLayout_gradientColors, -1)).toList()
			}
			if (ta.hasValue(R.styleable.RippleLinearLayout_gradientOrientation)) {
				gradientOrientation = GradientDrawable.Orientation.values()[ta.getInt(R.styleable.RippleLinearLayout_gradientOrientation, 0)]
			}
			if (ta.hasValue(R.styleable.RippleLinearLayout_corners)) {
				val aux = ta.getDimension(R.styleable.RippleLinearLayout_corners, -10f)
				if(aux > -1) corners = aux
			}
			if (ta.hasValue(R.styleable.RippleLinearLayout_cornerTopLeft)) {
				cornerTopLeft = ta.getDimension(R.styleable.RippleLinearLayout_cornerTopLeft, 0f)
			}
			if (ta.hasValue(R.styleable.RippleLinearLayout_cornerTopRight)) {
				cornerTopRight = ta.getDimension(R.styleable.RippleLinearLayout_cornerTopRight, 0f)
			}
			if (ta.hasValue(R.styleable.RippleLinearLayout_cornerBottomLeft)) {
				cornerBottomLeft = ta.getDimension(R.styleable.RippleLinearLayout_cornerBottomLeft, 0f)
			}
			if (ta.hasValue(R.styleable.RippleLinearLayout_cornerBottomRight)) {
				cornerBottomRight = ta.getDimension(R.styleable.RippleLinearLayout_cornerBottomRight, 0f)
			}
			if (ta.hasValue(R.styleable.RippleLinearLayout_strokeColor)) {
				strokeColor = ta.getColor(R.styleable.RippleLinearLayout_strokeColor, -1)
			}
			if (ta.hasValue(R.styleable.RippleLinearLayout_strokeWidth)) {
				strokeWidth = ta.getDimension(R.styleable.RippleLinearLayout_strokeWidth, 4.dpToPx().toFloat()).toInt()
			}
		} finally {
			ta.recycle()
		}
	}


}