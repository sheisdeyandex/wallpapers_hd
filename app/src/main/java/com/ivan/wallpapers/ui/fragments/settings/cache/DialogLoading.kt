package com.ivan.wallpapers.ui.fragments.settings.cache

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.ivan.wallpapers.MainApplication
import com.ivan.wallpapers.R
import com.ivan.wallpapers.databinding.DialogLoadWallpaperBinding
import java.io.File
import java.text.DecimalFormat
import kotlin.math.pow


class DialogLoading : DialogFragment() {
    fun File.calculateSizeRecursively(): Long {
        return walkBottomUp().fold(0L) { acc, file -> acc + file.length() }
    }
    fun getFileSize(size: Long): String {
        if (size <= 0) return "0"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (kotlin.math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(size / 1024.0.pow(digitGroups.toDouble()))
            .toString() + " " + units[digitGroups]
    }
    var binding: DialogLoadWallpaperBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogLoadWallpaperBinding.inflate(inflater, container, false)
        val v: View = binding!!.root
        dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setCanceledOnTouchOutside(true)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val size =context?.cacheDir?.calculateSizeRecursively()
        val sizeText = MainApplication.INSTANCE.getString(R.string.wholeCache)+ size?.let {
            getFileSize(
                it
            )
        }
        binding!!.tvCache.text = sizeText
        binding!!.mbtnCancel.setOnClickListener { dismiss() }
        binding!!.mbtnClear.setOnClickListener {
            context?.cacheDir?.deleteRecursively()
            dismiss()
        }
        return v
    }
}