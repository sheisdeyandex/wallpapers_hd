package com.wallpapers.hd.interfaces

import com.wallpapers.hd.ui.fragments.main.model.mainmodel.ResponseModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {
    @GET("photos.getById")
    fun originalPhoto(
        @Query("access_token") access_token: String,
        @Query("photos") photos:String,
        @Query("extended") extended:String,
        @Query("v") v:String
        )
    :Call<com.wallpapers.hd.ui.fragments.fullscreenwallpaper.model.ResponseModel>
    @GET("photos.get")
    fun listWallpapers(
        @Query("owner_id") owner_id:String,
        @Query("access_token") access_token:String,
        @Query("album_id") album_id:String,
        @Query("count") count:String,
        @Query("offset") offset:String,
        @Query("rev") rev:String,
        @Query("v") v:String): Call<ResponseModel>
    companion object {
        var BASE_URL = "https://api.vk.com/method/"
        fun create() : ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}