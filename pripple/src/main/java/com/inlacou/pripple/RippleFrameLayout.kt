package com.inlacou.pripple

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.FrameLayout
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Bitmap
import android.graphics.Paint.FILTER_BITMAP_FLAG
import android.os.Build

open class RippleFrameLayout: FrameLayout, Rippleable {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	override val viewContext: Context
		get() = context

	override var forceClipChildren: Boolean = false
		set(value) {
			if(value!=field) {
				field = value
				setBackground()
			}
		}
	override var normalColor: Int? = null
		set(value) {
			if(value!=null) {
				field = value
				setBackground()
			}
		}
	override var rippleColor: Int? = null
		set(value) {
			if(value!=null) {
				field = value
				setBackground()
			}
		}
	override var gradientColors: List<Int>? = null
		set(value) {
			if(value!=null) {
				field = value
				setBackground()
			}
		}
	override var gradientOrientation: GradientDrawable.Orientation? = null
		set(value) {
			if(value!=null) {
				field = value
				setBackground()
			}
		}
	override var gradientType: Rippleable.GradientTypes? = null
		set(value) {
			if(value!=null) {
				field = value
				setBackground()
			}
		}
	override var gradientRadius: Float? = null
		set(value) {
			if(value!=null) {
				field = value
				setBackground()
			}
		}
	override var gradientCenterX: Float? = null
		set(value) {
			if(value!=null) {
				field = value
				setBackground()
			}
		}
	override var gradientCenterY: Float? = null
		set(value) {
			if(value!=null) {
				field = value
				setBackground()
			}
		}
	/**
	 * In px
	 */
	override var corners: Float? = null
		set(value) {
			field = value
			setBackground()
		}
	/**
	 * In px
	 */
	override var cornerTopLeft: Float = 0f
		set(value) {
			field = value
			setBackground()
		}
	/**
	 * In px
	 */
	override var cornerTopRight: Float = 0f
		set(value) {
			field = value
			setBackground()
		}
	/**
	 * In px
	 */
	override var cornerBottomLeft: Float = 0f
		set(value) {
			field = value
			setBackground()
		}
	/**
	 * In px
	 */
	override var cornerBottomRight: Float = 0f
		set(value) {
			field = value
			setBackground()
		}
	override var strokeColor: Int? = null
		set(value) {
			if(value!=null) {
				field = value
				setBackground()
			}
		}
	/**
	 * In px
	 */
	override var strokeWidth: Int = 2.dpToPx()
		set(value) {
			field = value
			setBackground()
		}


	private fun setClickableOverChildren() {
		(0..childCount).mapNotNull { getChildAt(it) }.forEach { it.isClickable = false }
	}

	override fun setBackground() {
		super<Rippleable>.setBackground()
		setClickableOverChildren()
	}

	override fun setViewBackground(drawable: Drawable) {
		background = drawable
	}

	override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
		super.onLayout(changed, left, top, right, bottom)
		setBackground()
	}

	override fun setDraw(b: Boolean) {
		setWillNotDraw(b)
		requestDisallowInterceptTouchEvent(false)
	}

	override fun draw(canvas: Canvas) {
		val stroke = 0f//strokeWidth.toFloat()
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 && forceClipChildren && !isInEditMode) {
			canvas.clipPath(Path().apply {
				addRoundRect(
					RectF(stroke, stroke, width.toFloat()-stroke, height.toFloat()-stroke),
					floatArrayOf(
						corners ?: cornerTopLeft,
						corners ?: cornerTopLeft,
						corners ?: cornerTopRight,
						corners ?: cornerTopRight,
						corners ?: cornerBottomRight,
						corners ?: cornerBottomRight,
						corners ?: cornerBottomLeft,
						corners ?: cornerBottomLeft
					), Path.Direction.CW)
			})
		}
		super.draw(canvas)
	}
}