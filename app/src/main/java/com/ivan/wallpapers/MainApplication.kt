package com.ivan.wallpapers

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.akexorcist.localizationactivity.ui.LocalizationApplication
import com.github.terrakok.cicerone.Cicerone
import com.ivan.wallpapers.ui.fragments.main.model.mainmodel.SizesModel
import com.onesignal.OneSignal
import com.yandex.metrica.YandexMetrica

import com.yandex.metrica.YandexMetricaConfig
import java.util.*
import kotlin.collections.ArrayList

const val ONESIGNAL_APP_ID = "2dd2c3f0-0ff8-4aa4-b0c1-aa22b59a2d1d"

class MainApplication:LocalizationApplication() {
    override fun getDefaultLanguage(context: Context): Locale {
        return Locale.getDefault()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)

    }
    companion object{
        internal lateinit var INSTANCE: MainApplication
            private set
        val allWallpapersList = ArrayList<SizesModel>()
        val installWallpaper = MutableLiveData<String>()
        val mainFragmentLiveData = MutableLiveData<Boolean>()
        val fullScreenBack = MutableLiveData<Boolean>()
        val mainFragmentLoader = MutableLiveData<Boolean>()
        lateinit var wallpaper : Bitmap
        val lockScreen = "lockscreen"
        var type = ""
        val homeScreen = "homescreen"
        val both = "both"
        var photoId = ""
        var albumId =  "249956323"
        var locale =  ""
        var albumName = "Новинки"
    }

}