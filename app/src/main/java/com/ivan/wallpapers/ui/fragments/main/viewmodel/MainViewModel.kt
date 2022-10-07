package com.ivan.wallpapers.ui.fragments.main.viewmodel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ivan.wallpapers.MainApplication
import com.ivan.wallpapers.R
import com.ivan.wallpapers.interfaces.ApiInterface
import com.ivan.wallpapers.ui.fragments.main.model.mainmodel.ResponseModel
import com.ivan.wallpapers.ui.fragments.main.model.mainmodel.SizesModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    var sizeModelList = ArrayList<SizesModel>()
    val list = MutableLiveData<Boolean>()
    var offsetSize= 0
    fun makeRequest(album_id:String){
        val apiInterface = ApiInterface.create().listWallpapers(
            MainApplication.INSTANCE.getString(R.string.owner_id),
            MainApplication.INSTANCE.getString(R.string.access_token),
            album_id,"21", offsetSize.toString(),"1",
            MainApplication.INSTANCE.getString(R.string.version)
        )
        offsetSize= sizeModelList.size+21
        apiInterface.enqueue( object : Callback<ResponseModel> {
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                response.body()?.response?.items?.forEach {itemsModel->
                    if (itemsModel.sizes.isNotEmpty()){
                        itemsModel.sizes.forEach {
                            if (it.type=="p"){
                                sizeModelList.add(SizesModel(it.type, it.url, itemsModel.id))
                            }
                            if (!MainApplication.allWallpapersList.contains(SizesModel(it.type, it.url, itemsModel.id))){
                                MainApplication.allWallpapersList.add(SizesModel(it.type, it.url, itemsModel.id))
                            }
                        }
                    }
                } .also {
                    list.postValue(true)
                }?: list.postValue(false)
            }
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                list.postValue(false)
            }
        })
    }
}