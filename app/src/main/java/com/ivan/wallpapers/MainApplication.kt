package com.ivan.wallpapers

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.akexorcist.localizationactivity.ui.LocalizationApplication
import com.github.terrakok.cicerone.Cicerone
import com.ivan.wallpapers.ui.fragments.main.model.CategoriesModel
import com.ivan.wallpapers.ui.fragments.main.model.mainmodel.SizesModel
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp
import java.util.*

const val ONESIGNAL_APP_ID = "2dd2c3f0-0ff8-4aa4-b0c1-aa22b59a2d1d"
@HiltAndroidApp
class MainApplication:LocalizationApplication() {
    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()

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
        val prefixes = MutableLiveData< ArrayList<CategoriesModel>>()
        val installWallpaper = MutableLiveData<String>()
        val mainFragmentLiveData = MutableLiveData<Boolean>()
        val fullScreenBack = MutableLiveData<Boolean>()
        val mainFragmentLoader = MutableLiveData<Boolean>()
        var wallpaper : Bitmap? = null
        val lockScreen = "lockscreen"
        var type = ""
        val homeScreen = "homescreen"
        val both = "both"
        var photoId:String =""
        var thumbUrl:String =""
        var sizedModel:SizesModel? = null
        var fullUrl:String =""
        var photoName:String = ""
        var photoPath:String = ""
        var albumId =  "249956323"
        var albumName = "Новинки"
    }

}