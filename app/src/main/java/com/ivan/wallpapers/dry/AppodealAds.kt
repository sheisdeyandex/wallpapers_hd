package com.ivan.wallpapers.dry

import android.app.Activity
import com.appodeal.ads.Appodeal

object AppodealAds {
    fun showInter(activity:Activity){
        if (Appodeal.isLoaded(Appodeal.INTERSTITIAL)){
            Appodeal.show(activity, Appodeal.INTERSTITIAL)
        }
    }
}