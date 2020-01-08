package com.inlacou.pripple

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.FrameLayout
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Bitmap
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.graphics.Paint.FILTER_BITMAP_FLAG
import android.graphics.Paint.ANTI_ALIAS_FLAG







open class RippleFrameLayout: FrameLayout, Rippleable {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	override val viewContext: Context
		get() = context

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


	private fun setClickableOverChilds() {
		(0 .. childCount)
				.map { getChildAt(it) }
				.filter { it!=null }
				.forEach { it.isClickable = false }
	}

	override fun setBackground() {
		super<Rippleable>.setBackground()
		setClickableOverChilds()
	}

	override fun setViewBackground(drawable: Drawable) {
		background = drawable
	}

	override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
		super.onLayout(changed, left, top, right, bottom)
		setBackground()
	}

	private var maskBitmap: Bitmap? = null
	private val paint: Paint = Paint(ANTI_ALIAS_FLAG)
	private val maskPaint: Paint = Paint(ANTI_ALIAS_FLAG or FILTER_BITMAP_FLAG).apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

	override fun draw(canvas: Canvas) {
		val offscreenBitmap = Bitmap.createBitmap(canvas.width, canvas.height, Bitmap.Config.ARGB_8888)
		val offscreenCanvas = Canvas(offscreenBitmap)

		super.draw(offscreenCanvas)

		if (maskBitmap == null) {
			maskBitmap = createMask(canvas.width, canvas.height)
		}

		offscreenCanvas.drawBitmap(maskBitmap, 0f, 0f, maskPaint)
		canvas.drawBitmap(offscreenBitmap, 0f, 0f, paint)
	}

	private fun createMask(width: Int, height: Int): Bitmap {
		val mask = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8)
		val canvas = Canvas(mask)

		val paint = Paint(ANTI_ALIAS_FLAG)
		paint.color = Color.WHITE

		canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

		paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

		val path = Path()
		path.addRoundRect(RectF(0f, 0f, width.toFloat(), height.toFloat()), floatArrayOf(
			corners ?: cornerTopLeft,
			corners ?: cornerTopLeft,
			corners ?: cornerTopRight,
			corners ?: cornerTopRight,
			corners ?: cornerBottomRight,
			corners ?: cornerBottomRight,
			corners ?: cornerBottomLeft,
			corners ?: cornerBottomLeft
		), Path.Direction.CW)

		canvas.drawPath(path, paint)

		return mask
	}

}