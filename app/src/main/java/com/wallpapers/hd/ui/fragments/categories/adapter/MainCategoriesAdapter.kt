package com.wallpapers.hd.ui.fragments.categories.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wallpapers.hd.MainActivity
import com.wallpapers.hd.MainApplication
import com.wallpapers.hd.R
import com.wallpapers.hd.databinding.ItemMainCategoriesBinding
import com.wallpapers.hd.dry.GlideMainLoader
import com.wallpapers.hd.ui.fragments.main.model.CategoriesModel


class MainCategoriesAdapter(val context: Context,val activity:Activity):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var glideMainLoader: GlideMainLoader? = null
    private val differCallback = object : DiffUtil.ItemCallback<CategoriesModel>() {
        override fun areItemsTheSame(oldItem: CategoriesModel, newItem: CategoriesModel): Boolean {
            return oldItem.name == newItem.name
        }
        override fun areContentsTheSame(oldItem: CategoriesModel, newItem: CategoriesModel): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding = ItemMainCategoriesBinding.inflate(LayoutInflater.from(context),parent,false)
        return PhotoHolder(binding)
    }
    inner class PhotoHolder(private val binding: ItemMainCategoriesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(categoryName: CategoriesModel){
            binding.tvCategoryName.text = categoryName.name
            glideMainLoader = GlideMainLoader(context,
                categoryName.icon,
                binding.skvLoading,
                binding.ivCategories)
            glideMainLoader?.loadImage()
            binding.clCategoriesItem.setOnClickListener {
                MainApplication.albumId = categoryName.id
                MainApplication.albumName = categoryName.name
                MainApplication.mainFragmentLoader.postValue(true)
                (activity as MainActivity).binding.bnvWallpaperSettings.selectedItemId = R.id.mainFragment


            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is PhotoHolder){
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



