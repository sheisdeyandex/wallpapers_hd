package com.ivan.wallpapers.ui.fragments.main.view.adapter
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.ivan.wallpapers.MainActivity
import com.ivan.wallpapers.MainApplication
import com.ivan.wallpapers.R
import com.ivan.wallpapers.databinding.WallpapersItemBinding
import com.ivan.wallpapers.dry.NavigationHelper
import com.ivan.wallpapers.dry.Screens
import com.ivan.wallpapers.ui.fragments.fullscreenwallpaper.view.FullScreenWallpaper
import com.ivan.wallpapers.ui.fragments.main.model.mainmodel.SizesModel
import com.ivan.wallpapers.ui.fragments.main.view.MainFragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception


class MainAdapter (private val context: Context,val fragment: MainFragment):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val differCallback = object : DiffUtil.ItemCallback<SizesModel>() {
        override fun areItemsTheSame(oldItem: SizesModel, newItem: SizesModel): Boolean {
            return oldItem.url == newItem.url
        }
        override fun areContentsTheSame(oldItem: SizesModel, newItem: SizesModel): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding = WallpapersItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return MainViewHolder(binding)
    }
    inner class MainViewHolder(private val binding: WallpapersItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(sizesModel: SizesModel) {
            Glide.with(context).load(sizesModel.url).centerCrop().listener(object :RequestListener<Drawable?>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d("sizes", "width: "+binding.ivWallpapers.width.toString()+"height: "+binding.ivWallpapers.height.toString())
                    binding.skvLoading.isVisible = false
                    return false
                }
            }).into(binding.ivWallpapers)
//            Picasso.get().load(sizesModel.url).fit().centerCrop().into(binding.ivWallpapers, object :Callback{
//                override fun onSuccess() {
//                    binding.skvLoading.visibility = View.GONE
//                }
//
//                override fun onError(e: Exception?) {
//                }
//            })
            binding.ivWallpapers.setOnClickListener {
                (fragment.requireActivity() as MainActivity).replaceFragment(R.id.action_mainFragment_to_fullScreenWallpaper)
                MainApplication.photoId = sizesModel.id
            }
//            Glide.with(binding.ivWallpapers.context).load(sizesModel.url).listener(object :RequestListener<Drawable?>{
//                override fun onLoadFailed(
//                    e: GlideException?,
//                    model: Any?,
//                    target: Target<Drawable?>?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    return false
//                }
//
//                override fun onResourceReady(
//                    resource: Drawable?,
//                    model: Any?,
//                    target: Target<Drawable?>?,
//                    dataSource: DataSource?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    binding.skvLoading.visibility = View.GONE
//                    return false
//                }
//            }).fitCenter().into(binding.ivWallpapers)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MainViewHolder){
            val foodItem = differ.currentList[position]
            holder.bind(foodItem)
        }

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
