package com.inlacou.pripple.tristate

import android.content.Context
import android.util.AttributeSet
import com.inlacou.pripple.RippleRelativeLayout
import com.inlacou.pripple.RippleView
import com.inlacou.pripple.RippleableMdl

open class TristateRippleView: RippleView {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }
	
	var special: Boolean = false
		set(value) {
			field = value
			update()
		}
	
	var normalTheme: RippleableMdl? = null
	var specialTheme: RippleableMdl? = null
	var specialDisabledTheme: RippleableMdl? = null
	var disabledTheme: RippleableMdl? = null
	
	override fun setEnabled(enabled: Boolean) {
		super.setEnabled(enabled)
		update()
	}
	
	fun update() {
		when {
			!isEnabled && special -> {
				// not enabled and special -> disabled special
				specialDisabledTheme
			}
			!isEnabled && !special -> {
				// not enabled and not special -> disabled normal
				disabledTheme
			}
			isEnabled && special -> {
				// enabled and special -> special
				specialTheme
			}
			isEnabled && !special -> {
				// enabled and not special -> normal
				normalTheme
			}
			else -> null
		}?.let {
			applyMdl(it)
		}
	}
	
}