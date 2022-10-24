package com.ivan.wallpapers.ui.fragments.favourites.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouritesModel(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "thumb_url") val thumbUrl: String?,
    @ColumnInfo(name = "fullscreen_url") val fullScreenUrl: String?
)