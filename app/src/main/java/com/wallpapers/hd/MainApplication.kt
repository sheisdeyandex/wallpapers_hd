package com.wallpapers.hd

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.akexorcist.localizationactivity.ui.LocalizationApplication
import com.wallpapers.hd.ui.fragments.main.model.mainmodel.SizesModel
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp
import java.util.*

const val ONESIGNAL_APP_ID = ""
@HiltAndroidApp
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
        var albumId =  "249956323"
        var albumName = "Новинки"
    }

}