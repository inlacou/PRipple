package com.inlacou.pripple

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class RippleLinearLayout: LinearLayout, Rippleable {
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
	var pressedColor: Int? = null
		set(value) {
			if(value!=null) {
				field = value
				setBack()
			}
		}
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
		}
	var strokeWidth: Int = 2.dpToPx()
		set(value) {
			field = value
			setBack()
		}

	private fun setBack() {
		normalColor?.let { normalColor ->
			pressedColor?.let { pressedColor ->
				background = getPressedColorRippleDrawable(normalColor, pressedColor, corners, strokeColor, strokeWidth)
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
		setBack()
	}

	protected open fun readAttrs(attrs: AttributeSet) {
		val ta = context.obtainStyledAttributes(attrs, R.styleable.RippleLinearLayout, 0, 0)
		try {
			if (ta.hasValue(R.styleable.RippleLinearLayout_normal)) {
				normalColor = ta.getColor(R.styleable.RippleLinearLayout_normal, -1)
			}
			if (ta.hasValue(R.styleable.RippleLinearLayout_ripple)) {
				pressedColor = ta.getColor(R.styleable.RippleLinearLayout_ripple, -1)
			}
			if (ta.hasValue(R.styleable.RippleLinearLayout_corners)) {
				corners = ta.getDimension(R.styleable.RippleLinearLayout_corners, 4.dpToPx().toFloat())
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