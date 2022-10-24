package com.ivan.wallpapers.dry

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.ivan.wallpapers.MainApplication

object NavigationHelper {
    fun navigate(fragment:FragmentScreen){
        MainApplication.INSTANCE.router.navigateTo(fragment)
    }
    fun backTo(fragment: FragmentScreen){
        MainApplication.INSTANCE.router.backTo(fragment)
    }

}