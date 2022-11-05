package com.wallpapers.hd.ui.fragments.favourites.viewmodel

import androidx.lifecycle.ViewModel
import com.wallpapers.hd.repository.FavouritesRepository
import com.wallpapers.hd.ui.fragments.main.model.mainmodel.SizesModel
import dagger.hilt.android.lifecycle.HiltViewModel
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