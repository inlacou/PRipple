package com.inlacou.pripple

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import java.lang.Exception
import java.lang.IllegalArgumentException

open class RippleButton: AppCompatTextView, Rippleable {
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
	override val clickable get() = isClickable

	/**
	 * In px
	 */
	override var strokeWidth: Int = 2.dpToPx()
		set(value) {
			field = value
			setBackground()
		}
	override var clickableOverChildren: Boolean
		get() = false
		set(value) {}

	override fun setViewBackground(drawable: Drawable) {
		background = drawable
	}

	override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
		super.onLayout(changed, left, top, right, bottom)
		setBackground()
	}

	override fun setDraw(b: Boolean) {
		setWillNotDraw(b)
	}

	override fun draw(canvas: Canvas) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 && forceClipChildren && !isInEditMode) {
			canvas.clipPath(Path().apply {
				addRoundRect(
					RectF(0f, 0f, width.toFloat(), height.toFloat()), floatArrayOf(
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
		try {
			super.draw(canvas)
		}catch (e: IllegalArgumentException) {
			Log.e("exception on RippleLL", "Data | left: ${0f} top: ${0f} right: ${width.toFloat()} bottom: ${height.toFloat()} | corners topLeft: $cornerTopLeft topRight: $cornerTopRight bottomRight: $cornerBottomRight bottomLeft: $cornerBottomLeft | pathDirection: ${Path.Direction.CW} | Exception: ${e.message}")
		}
	}
}