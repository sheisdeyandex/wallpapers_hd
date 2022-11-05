package com.wallpapers.hd.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wallpapers.hd.dao.FavouritesDao
import com.wallpapers.hd.dry.DateConverter
import com.wallpapers.hd.ui.fragments.main.model.mainmodel.SizesModel

@Database(entities = [SizesModel::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouritesDao(): FavouritesDao
}