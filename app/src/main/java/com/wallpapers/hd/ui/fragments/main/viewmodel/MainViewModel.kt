package com.wallpapers.hd.ui.fragments.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wallpapers.hd.MainApplication
import com.wallpapers.hd.R
import com.wallpapers.hd.interfaces.ApiInterface
import com.wallpapers.hd.ui.fragments.main.model.mainmodel.ResponseModel
import com.wallpapers.hd.ui.fragments.main.model.mainmodel.SizesModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainViewModel : ViewModel() {
    var sizeModelList = ArrayList<SizesModel>()
    val list = MutableLiveData<Boolean>()
    var offsetSize= 0
    var albumId = MainApplication.albumId
    init {
        makeRequest(MainApplication.albumId)
    }
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
                            if (it.type=="q"){
                                sizeModelList.add(SizesModel(it.type, it.url, itemsModel.id, Calendar.getInstance().time,0))
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