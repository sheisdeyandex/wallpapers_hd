package com.ivan.wallpapers.dry


import android.content.Context
import androidx.core.content.ContextCompat
import com.ivan.wallpapers.MainApplication
import com.ivan.wallpapers.R
import com.ivan.wallpapers.ui.fragments.main.model.CategoriesModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

@Module
@InstallIn(FragmentComponent::class)
class Categories @Inject constructor() {
    @Provides
    fun getCategoriesList(@ActivityContext context:Context, sort: String):List<CategoriesModel>{
        val categoriesList = ArrayList<CategoriesModel>()
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_dusk),
            MainApplication.INSTANCE.getString(R.string.album_id_dusk),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_fog),
            MainApplication.INSTANCE.getString(R.string.album_id_fog),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_art),
            MainApplication.INSTANCE.getString(R.string.album_id_art),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Loneliness),
            MainApplication.INSTANCE.getString(R.string.album_id_loneliness),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Human),
            MainApplication.INSTANCE.getString(R.string.album_id_human),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Hand),
            MainApplication.INSTANCE.getString(R.string.album_id_hand),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_black_and_white),
            MainApplication.INSTANCE.getString(R.string.album_id_black_and_white),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Retro),
            MainApplication.INSTANCE.getString(R.string.album_id_retro),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Words),
            MainApplication.INSTANCE.getString(R.string.album_id_words),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_3D),
            MainApplication.INSTANCE.getString(R.string.album_id_3d),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_MUSIC),
            MainApplication.INSTANCE.getString(R.string.album_id_music),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Couple),
            MainApplication.INSTANCE.getString(R.string.album_id_couple),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_FANTASY),
            MainApplication.INSTANCE.getString(R.string.album_id_fantasy),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Funny),
            MainApplication.INSTANCE.getString(R.string.album_id_funny),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_black),
            MainApplication.INSTANCE.getString(R.string.album_id_black),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_tending_movies),
            MainApplication.INSTANCE.getString(R.string.album_id_trending_movies),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Flowers),
            MainApplication.INSTANCE.getString(R.string.album_id_flowers),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Motivation),
            MainApplication.INSTANCE.getString(R.string.album_id_motivation),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Sport),
            MainApplication.INSTANCE.getString(R.string.album_id_sport),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Fields),
            MainApplication.INSTANCE.getString(R.string.album_id_fields),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Autumn),
            MainApplication.INSTANCE.getString(R.string.album_id_autumn),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Night),
            MainApplication.INSTANCE.getString(R.string.album_id_night),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Water),
            MainApplication.INSTANCE.getString(R.string.album_id_water),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Snow),
            MainApplication.INSTANCE.getString(R.string.album_id_snow),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Sky),
            MainApplication.INSTANCE.getString(R.string.album_id_sky),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Cities),
            MainApplication.INSTANCE.getString(R.string.album_id_cities),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Sunset),
            MainApplication.INSTANCE.getString(R.string.album_id_sunset),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Mountains),
            MainApplication.INSTANCE.getString(R.string.album_id_mountains),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Nature),
            MainApplication.INSTANCE.getString(R.string.album_id_nature),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Animals),
            MainApplication.INSTANCE.getString(R.string.album_id_animals),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Abstractions),
            MainApplication.INSTANCE.getString(R.string.album_id_abstractions),
            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Games),
            MainApplication.INSTANCE.getString(R.string.album_id_games),
            ContextCompat.getDrawable(context, R.drawable.trans)))

        return sortCategory(categoriesList, sort)
    }
    private fun sortCategory (categoriesList:ArrayList<CategoriesModel>, sort: String):List<CategoriesModel>{
        return if (sort == "alphabetOrder"){
            val sortedList = categoriesList.sortedBy { it.name }
            sortedList
        } else{
            categoriesList
        }
    }
}