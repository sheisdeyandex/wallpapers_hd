package com.ivan.wallpapers.dry

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class GlideMainLoader(
    var context: Context?,
    var url: String,
    var loadingView: View,
    var imageView:ImageView) {

    fun loadImage(){
        context?.let {
            Glide.with(it).load(url).centerCrop().listener(object :
                RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    loadingView.isVisible = false
                    return false
                }
            }).transition(DrawableTransitionOptions.withCrossFade(500)) //for loading image smoothly and fastly.
                .into(imageView)
        }
    }
}