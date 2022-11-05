package com.wallpapers.hd.dry

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Inject

@Module
@InstallIn(FragmentComponent::class)
class PhotoPermission @Inject constructor(){
    @Provides
    fun checkPhotoPermission(context: Context):Boolean {
        val permissionWrite = ContextCompat.checkSelfPermission(
            context, Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return permissionWrite== PackageManager.PERMISSION_GRANTED
    }

    fun grandPhotoSavePermission(context: Context):Boolean {
        val permissionWrite = ContextCompat.checkSelfPermission(
            context, Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return permissionWrite== PackageManager.PERMISSION_GRANTED
    }
}