package com.ivan.wallpapers.ui.fragments.fullscreenwallpaper.viewmodel

import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ivan.wallpapers.MainApplication
import com.ivan.wallpapers.R
import com.ivan.wallpapers.interfaces.ApiInterface
import com.ivan.wallpapers.ui.fragments.fullscreenwallpaper.model.ResponseModel
import retrofit2.Call
import retrofit2.Response
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import javax.security.auth.callback.Callback

class FullScreenWallpaperViewModel : ViewModel() {
    var finished = MutableLiveData<Boolean>()
    var fullScreenWallpaper = MutableLiveData<String>()
    fun mLoad(string: String): Bitmap? {
        val url: URL = mStringToURL(string)!!
        val connection: HttpURLConnection?
        try {
            connection = url.openConnection() as HttpURLConnection
            connection.connect()
            val inputStream: InputStream = connection.inputStream
            val bufferedInputStream = BufferedInputStream(inputStream)
            return BitmapFactory.decodeStream(bufferedInputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
    private fun mStringToURL(string: String): URL? {
        try {
            return URL(string)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
    }
    fun mSaveMediaToStorage(bitmap: Bitmap?, context: Context, name:String) {
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, name)
            fos = FileOutputStream(image)
        }
        fos?.use {
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
    }

    fun makeOrigPhotoRequest(access_token:String, photo_id:String){
        ApiInterface.create().originalPhoto(
            access_token,
            MainApplication.INSTANCE.getString(R.string.owner_id)+"_"+ photo_id, "1",
            MainApplication.INSTANCE.getString(R.string.version)).enqueue(
            object :retrofit2.Callback<ResponseModel>{
                override fun onResponse(
                    call: Call<ResponseModel>,
                    response: Response<ResponseModel>
                ) {
                    response.body().let {
                        it?.response?.forEach{
                            fullScreenWallpaper.postValue(it.orig_photo.url)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {

                }
            })
    }
    fun installWallpaper(wallpaper: Bitmap, context: Context){
        val myWallpaperManager = WallpaperManager.getInstance(context)
        try {
            myWallpaperManager.setBitmap(wallpaper)
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }
    fun installLockScreenWallpaper(wallpaper: Bitmap,context:Context){
        val wallpaperManager = WallpaperManager.getInstance(context)
        try {
            wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_LOCK)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    private fun saveImageInQ(bitmap: Bitmap, name:String, context: Context): Uri? {
        val cw = ContextWrapper(context)
        val fos: OutputStream?
        val directory: File = cw.getDir("imageDir", Context.MODE_PRIVATE)
        // Create imageDir
        val mypath = File(directory, name)
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            put(MediaStore.MediaColumns.RELATIVE_PATH,mypath.path)
            put(MediaStore.MediaColumns.IS_PENDING, 1)
        }

        //use application context to get contentResolver
        val contentResolver = context.contentResolver
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        uri?.let { contentResolver.openOutputStream(it) }.also { fos = it }
        fos?.use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
        fos?.flush()
        fos?.close()

        contentValues.clear()
        contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
        uri?.let {
            finished.postValue(true)
            contentResolver.update(it, contentValues, null, null)
        }?:finished.postValue(true)
        return uri
    }
    fun saveToInternalStorage(bitmapImage: Bitmap, name:String, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { //this one
            saveImageInQ(bitmapImage,name, context)
        }
        else{
            val cw = ContextWrapper(context)
            // path to /data/data/yourapp/app_data/imageDir
            val directory: File = cw.getDir("imageDir", Context.MODE_PRIVATE)
            // Create imageDir
            val mypath = File(directory, name)
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(mypath)
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    finished.postValue(true)
                    fos?.close()
                } catch (e: IOException) {
                    finished.postValue(true)
                    e.printStackTrace()
                }
            }
        }

    }
}