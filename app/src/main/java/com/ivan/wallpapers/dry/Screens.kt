package com.ivan.wallpapers.dry

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.ivan.wallpapers.ui.fragments.categories.CategoriesFragment
import com.ivan.wallpapers.ui.fragments.favourites.view.FavouritesFragment
import com.ivan.wallpapers.ui.fragments.fullscreenwallpaper.view.DialogInstall
import com.ivan.wallpapers.ui.fragments.fullscreenwallpaper.view.FullScreenWallpaper
import com.ivan.wallpapers.ui.fragments.main.view.MainFragment
import com.ivan.wallpapers.ui.fragments.nointernet.NoInternetFragment
import com.ivan.wallpapers.ui.fragments.settings.cache.DialogLoading
import com.ivan.wallpapers.ui.fragments.settings.language.LanguageFragment
import com.ivan.wallpapers.ui.fragments.settings.removeads.RemoveAdsFragment


object Screens {
    fun main() = FragmentScreen { MainFragment() }
    fun categories() = FragmentScreen { CategoriesFragment() }
    fun fullScreen(backTo:FragmentScreen) = FragmentScreen { FullScreenWallpaper(backTo) }
    fun language() = FragmentScreen { LanguageFragment() }
    fun disableAds() = FragmentScreen { RemoveAdsFragment() }
    fun installDialog() = FragmentScreen { DialogInstall() }
    fun cacheDialog() = FragmentScreen { DialogLoading() }
    fun noInternet() = FragmentScreen { NoInternetFragment() }
    fun favourites() = FragmentScreen { FavouritesFragment() }

}