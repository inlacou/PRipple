package com.inlacou.pripple

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.RelativeLayout

open class RippleRelativeLayout: RelativeLayout, Rippleable {
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

	fun setBackground(gradientDrawable: GradientDrawable) {
		normalColor?.let { normalColor ->
			background = getRippleDrawable(gradientDrawable, normalColor, rippleColor,
				floatArrayOf(corners ?: cornerTopLeft, corners ?: cornerTopLeft, corners ?: cornerTopRight, corners ?: cornerTopRight,
					corners ?: cornerBottomRight, corners ?: cornerBottomRight, corners ?: cornerBottomLeft, corners ?: cornerBottomLeft),
				strokeColor, strokeWidth)
		}
	}

	fun setBackground(colors: List<Int>, orientation: GradientDrawable.Orientation) {
		background = getRippleDrawable(colors, orientation, rippleColor,
			floatArrayOf(corners ?: cornerTopLeft, corners ?: cornerTopLeft, corners ?: cornerTopRight, corners ?: cornerTopRight,
				corners ?: cornerBottomRight, corners ?: cornerBottomRight, corners ?: cornerBottomLeft, corners ?: cornerBottomLeft),
			strokeColor, strokeWidth)
	}

	private fun setBackground() {
		normalColor?.let { normalColor ->
			background = getRippleDrawable(normalColor, rippleColor,
				floatArrayOf(corners ?: cornerTopLeft, corners ?: cornerTopLeft, corners ?: cornerTopRight, corners ?: cornerTopRight,
					corners ?: cornerBottomLeft, corners ?: cornerBottomLeft, corners ?: cornerBottomRight, corners ?: cornerBottomRight),
				strokeColor, strokeWidth)
		}
		setClickableOverChilds()
	}

	private fun setClickableOverChilds(){
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
		val ta = context.obtainStyledAttributes(attrs, R.styleable.RippleRelativeLayout, 0, 0)
		try {
			if (ta.hasValue(R.styleable.RippleRelativeLayout_normal)) {
				normalColor = ta.getColor(R.styleable.RippleRelativeLayout_normal, -1)
			}
			if (ta.hasValue(R.styleable.RippleRelativeLayout_ripple)) {
				rippleColor = ta.getColor(R.styleable.RippleRelativeLayout_ripple, -1)
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
			if (ta.hasValue(R.styleable.RippleRelativeLayout_strokeColor)) {
				strokeColor = ta.getColor(R.styleable.RippleRelativeLayout_strokeColor, -1)
			}
			if (ta.hasValue(R.styleable.RippleRelativeLayout_strokeWidth)) {
				strokeWidth = ta.getDimension(R.styleable.RippleRelativeLayout_strokeWidth, 4.dpToPx().toFloat()).toInt()
			}
		} finally {
			ta.recycle()
		}
	}


}