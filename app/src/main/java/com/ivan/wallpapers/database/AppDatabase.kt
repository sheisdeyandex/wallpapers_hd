package com.ivan.wallpapers.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ivan.wallpapers.dao.FavouritesDao
import com.ivan.wallpapers.dry.DateConverter
import com.ivan.wallpapers.ui.fragments.favourites.model.FavouritesModel
import com.ivan.wallpapers.ui.fragments.main.model.mainmodel.SizesModel

@Database(entities = [SizesModel::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouritesDao(): FavouritesDao
}