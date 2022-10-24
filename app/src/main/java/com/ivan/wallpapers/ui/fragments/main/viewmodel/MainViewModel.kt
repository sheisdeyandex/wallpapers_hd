package com.ivan.wallpapers.ui.fragments.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.ktx.storage
import com.ivan.wallpapers.MainApplication
import com.ivan.wallpapers.R
import com.ivan.wallpapers.interfaces.ApiInterface
import com.ivan.wallpapers.ui.fragments.main.model.WallpapersModel
import com.ivan.wallpapers.ui.fragments.main.model.mainmodel.ResponseModel
import com.ivan.wallpapers.ui.fragments.main.model.mainmodel.SizesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel : ViewModel() {
    var sizeModelList = ArrayList<SizesModel>()
    var wallpaperList = ArrayList<WallpapersModel>()
    val list = MutableLiveData<Boolean>()
    private val storage = Firebase.storage
    var firebasePageToken:String? = null
    var offsetSize= 0
    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                makeRequest(MainApplication.albumId)
            }
        }
     //   makeFirebaseRequest(MainApplication.albumId+"/thumbs", firebasePageToken)
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
//                            if (!MainApplication.allWallpapersList.contains(SizesModel(0,it.type, it.url, itemsModel.id))){
//                                MainApplication.allWallpapersList.add(SizesModel(0,it.type, it.url, itemsModel.id))
//                            }
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
    fun makeFirebaseRequest(path:String, pageToken:String?){
        val listRef = storage.reference.child(path)
        offsetSize= wallpaperList.size+21
        pageToken?.let {
            listRef.list(21, it).addOnSuccessListener {
                firebasePageToken = it.pageToken
                viewModelScope.launch {
                    getDownloadUrl(it)
                }
            }
        }?: listRef.list(21).addOnSuccessListener {
            firebasePageToken = it.pageToken
            viewModelScope.launch {
                getDownloadUrl(it)
            }
        }

    }
    var i = 0
    suspend fun getDownloadUrl(listResult:ListResult){
        val viewModelValue =
        viewModelScope.async(Dispatchers.IO){
            listResult.items.forEach {storageReference->
                storageReference.downloadUrl.addOnSuccessListener {imageUri->
                    imageUri.path?.replace("thumbs","")
                        ?.let { imagePath -> WallpapersModel(url= imageUri,name= storageReference.name, path =  imagePath) }
                        ?.let { wallpaperModel ->
                            if (!wallpaperList.contains(wallpaperModel)){
                                wallpaperList.add(wallpaperModel)
                                i++
                            }
                        }
                    if (wallpaperList.size==i){
                        list.postValue(true)
                    }
                    }
            }
        }
        viewModelValue.await()
    }
}