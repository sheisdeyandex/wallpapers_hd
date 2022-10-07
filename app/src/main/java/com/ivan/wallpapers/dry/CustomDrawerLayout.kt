package com.ivan.wallpapers.dry

import android.content.Context
import android.util.AttributeSet
import androidx.customview.widget.ViewDragHelper
import androidx.drawerlayout.widget.DrawerLayout
import java.lang.Integer.max

class CustomDrawerLayout : DrawerLayout {
    var swipeEdgeSize = 0

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        val leftDraggerField = DrawerLayout::class.java.getDeclaredField("mLeftDragger")
        val rightDraggerField = DrawerLayout::class.java.getDeclaredField("mRightDragger")
        rightDraggerField.isAccessible = true
        leftDraggerField.isAccessible = true
        val viewDragHelper = leftDraggerField[this] as ViewDragHelper
        val viewRightDragHelper = rightDraggerField[this] as ViewDragHelper

        val rightEdgeSizeField = ViewDragHelper::class.java.getDeclaredField("mEdgeSize")
        val edgeSizeField = ViewDragHelper::class.java.getDeclaredField("mEdgeSize")
        edgeSizeField.isAccessible = true
        rightEdgeSizeField.isAccessible = true
        val origEdgeSize = edgeSizeField[viewDragHelper] as Int
        val rightOrigEdgeSize = rightEdgeSizeField[viewRightDragHelper] as Int
        edgeSizeField.setInt(viewDragHelper, max(swipeEdgeSize, origEdgeSize))
        rightEdgeSizeField.setInt(viewRightDragHelper, max(swipeEdgeSize, rightOrigEdgeSize))
    }
}