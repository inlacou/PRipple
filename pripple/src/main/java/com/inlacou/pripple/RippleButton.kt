package com.inlacou.pripple

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

open class RippleButton: TextView, Rippleable {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	var normalColor: Int? = null
		set(value) {
			if(value!=null) {
				field = value
				setBack()
			}
		}
	var rippleColor: Int? = null
		set(value) {
			if(value!=null) {
				field = value
				setBack()
			}
		}/**
	 * In px
	 */
	var corners: Float = 4.dpToPx().toFloat()
		set(value) {
			field = value
			setBack()
		}
	var strokeColor: Int? = null
		set(value) {
			if(value!=null) {
				field = value
				setBack()
			}
		}/**
	 * In px
	 */
	var strokeWidth: Int = 2.dpToPx()
		set(value) {
			field = value
			setBack()
		}

	private fun setBack() {
		normalColor?.let { normalColor ->
			rippleColor?.let { pressedColor ->
				background = getPressedColorRippleDrawable(normalColor, pressedColor, corners, strokeColor, strokeWidth)
			}
		}
	}

	override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
		super.onLayout(changed, left, top, right, bottom)
		setBack()
	}

	protected open fun readAttrs(attrs: AttributeSet) {
		val ta = context.obtainStyledAttributes(attrs, R.styleable.RippleButton, 0, 0)
		try {
			if (ta.hasValue(R.styleable.RippleButton_normal)) {
				normalColor = ta.getColor(R.styleable.RippleButton_normal, -1)
			}
			if (ta.hasValue(R.styleable.RippleButton_ripple)) {
				rippleColor = ta.getColor(R.styleable.RippleButton_ripple, -1)
			}
			if (ta.hasValue(R.styleable.RippleButton_corners)) {
				corners = ta.getDimension(R.styleable.RippleButton_corners, 4.dpToPx().toFloat())
			}
			if (ta.hasValue(R.styleable.RippleButton_strokeColor)) {
				strokeColor = ta.getColor(R.styleable.RippleButton_strokeColor, -1)
			}
			if (ta.hasValue(R.styleable.RippleButton_strokeWidth)) {
				strokeWidth = ta.getDimension(R.styleable.RippleButton_strokeWidth, 4.dpToPx().toFloat()).toInt()
			}
		} finally {
			ta.recycle()
		}
	}
}