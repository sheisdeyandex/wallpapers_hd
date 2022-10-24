package com.ivan.wallpapers.repository

import com.ivan.wallpapers.dao.FavouritesDao
import com.ivan.wallpapers.ui.fragments.main.model.mainmodel.SizesModel
import javax.inject.Inject

class FavouritesRepository @Inject constructor(private val favouritesDao: FavouritesDao) {
    suspend fun getAllFavourites() = favouritesDao.getAllFavourites()
    suspend fun insetFavourite(favouritesModel: SizesModel?) = favouritesDao.insertFavourites(favouritesModel)
    suspend fun deleteFavourite(favouritesModel: SizesModel?) = favouritesDao.deleteFavourite(favouritesModel)
}