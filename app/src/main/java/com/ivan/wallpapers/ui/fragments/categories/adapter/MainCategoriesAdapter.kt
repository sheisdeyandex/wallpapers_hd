package com.ivan.wallpapers.ui.fragments.categories.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ivan.wallpapers.MainApplication
import com.ivan.wallpapers.databinding.ItemMainCategoriesBinding
import com.ivan.wallpapers.dry.NavigationHelper
import com.ivan.wallpapers.dry.Screens
import com.ivan.wallpapers.ui.fragments.main.model.CategoriesModel


class MainCategoriesAdapter(val context: Context):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
            Glide.with(context).load(categoryName.icon).centerCrop().into(binding.ivCategories)
            binding.clCategoriesItem.setOnClickListener {
                NavigationHelper.navigate(Screens.main())
                MainApplication.mainFragmentLoader.postValue(true)
                MainApplication.albumId = categoryName.id
                MainApplication.albumName = categoryName.name
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



