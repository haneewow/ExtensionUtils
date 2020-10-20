package com.intersvyaz.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

fun View.transformToBitmap(width: Int, height: Int): Bitmap {
    val bitmap: Bitmap
    this.run {
        measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY))

        layout(0, 0, measuredWidth, measuredHeight)

        bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
        val c = Canvas(bitmap)

        background?.draw(c)
        draw(c)
    }
    return bitmap
}

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setInvisible() {
    visibility = View.INVISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}

fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}
