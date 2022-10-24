package com.ivan.wallpapers.ui.fragments.favourites.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivan.wallpapers.MainApplication
import com.ivan.wallpapers.repository.FavouritesRepository
import com.ivan.wallpapers.ui.fragments.favourites.model.FavouritesModel
import com.ivan.wallpapers.ui.fragments.main.model.mainmodel.SizesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val repository: FavouritesRepository) : ViewModel() {
    suspend fun getAllFavourites() : List<SizesModel?> {
        return repository.getAllFavourites()
    }
    init {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO){
//                getAllFavourites()
//            }
//        }
    }
//    private suspend fun deleteFromFavourites(){
//        repository.deleteFavourite()
//    }
}