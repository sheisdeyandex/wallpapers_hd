package com.ivan.wallpapers.dry

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.ivan.wallpapers.ui.fragments.fullscreenwallpaper.view.FullScreenWallpaper
import com.ivan.wallpapers.ui.fragments.main.view.MainFragment

object Screens {
    fun mainFragment() = FragmentScreen { MainFragment() }
    fun fullScreenFragment() = FragmentScreen { FullScreenWallpaper() }
}