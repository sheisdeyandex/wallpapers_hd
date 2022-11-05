package com.wallpapers.hd.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.wallpapers.hd.ui.fragments.main.model.mainmodel.SizesModel

@Dao
interface FavouritesDao {
    @Query("SELECT * FROM sizesModel ORDER BY dateAdded DESC")
    fun getAllFavourites(): List<SizesModel?>

    @Insert
    fun insertFavourites(favourites: SizesModel?)

    @Delete
    fun deleteFavourite(favourite: SizesModel?)
//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User

}