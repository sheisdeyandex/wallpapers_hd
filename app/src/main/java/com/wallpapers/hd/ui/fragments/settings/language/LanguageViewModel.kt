package com.wallpapers.hd.ui.fragments.settings.language

import androidx.lifecycle.ViewModel
import com.wallpapers.hd.MainApplication
import com.wallpapers.hd.R
import com.wallpapers.hd.ui.fragments.settings.language.model.LanguagesModel

class LanguageViewModel : ViewModel() {

    fun addLanguagesToList():ArrayList<LanguagesModel>{
        val languagesList = ArrayList<LanguagesModel>()
        val stringArray= MainApplication.INSTANCE.resources.getStringArray(R.array.language)
        stringArray.forEach {
            val languageAndCode = it.split(",")
            val language = languageAndCode[0]
            var languageCode = ""
            if (languageAndCode.size>1){
                languageCode = languageAndCode[1]
            }
            languagesList.add(LanguagesModel(language, languageCode))
        }.also {
            return languagesList
        }

    }
}