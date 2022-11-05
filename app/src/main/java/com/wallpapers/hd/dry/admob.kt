package com.wallpapers.hd.dry

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

object admob {
    var mInterstitialAd: InterstitialAd? = null
    fun load(context: Context){
        val adRequest = AdRequest.Builder().build()
        var i=0
        InterstitialAd.load(context,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
                Log.d("suka", adError.message)
//                i++
//                if (i<5){
//                    load(context)
//                }
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })
    }
    fun show(activity: Activity){
        mInterstitialAd?.show(activity)
        load(activity)

    }
}