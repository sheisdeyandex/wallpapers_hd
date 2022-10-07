package com.ivan.wallpapers.dry

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.ivan.wallpapers.MainApplication

object NavigationHelper {
    fun navigate(fragment:Int, navController: NavController){
        navController.navigate(fragment)
    }
}