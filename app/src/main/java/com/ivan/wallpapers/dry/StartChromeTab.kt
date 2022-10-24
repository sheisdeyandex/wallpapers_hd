package com.ivan.wallpapers.dry

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

object StartChromeTab {
    fun openBrowser(url:String,  context:Context){
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }
}