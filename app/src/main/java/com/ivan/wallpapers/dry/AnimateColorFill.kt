package com.ivan.wallpapers.dry

import android.animation.ValueAnimator
import android.graphics.PorterDuff
import android.widget.ImageView

object AnimateColorFill {
    fun animateColorFill(
        startColor:Int,
        endColor:Int,
        duration:Long,
        imageView: ImageView
    )
    {
        val colorAnimation= ValueAnimator.ofArgb(startColor, endColor)
        colorAnimation.duration = duration
        colorAnimation.addUpdateListener {
            imageView.setColorFilter(startColor, PorterDuff.Mode.SRC_ATOP)
           // imageView.setColorFilter(it.animatedValue as Int, PorterDuff.Mode.SRC_IN)
        }
        colorAnimation.start()
    }
}