package com.ivan.wallpapers.dry

import android.content.Context
import android.content.Intent
import com.ivan.wallpapers.BuildConfig
import com.ivan.wallpapers.R


object Share  {
    fun share( context: Context){
        return context.
        startActivity(
            Intent.createChooser(
                shareIntent(context),
                context.getString(R.string.share)))
    }
    private fun shareIntent( context: Context):Intent{
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name))
        var shareMessage = context.getString(R.string.share_text)
        shareMessage =
            """
                    ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}      
                        """.trimIndent()
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        return shareIntent
    }

}