package com.ivan.wallpapers.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ivan.wallpapers.MainActivity
import com.ivan.wallpapers.MainApplication
import com.ivan.wallpapers.R
import com.ivan.wallpapers.databinding.CategoriesItemBinding
import com.ivan.wallpapers.dry.NavigationHelper
import com.ivan.wallpapers.dry.Screens
import com.ivan.wallpapers.ui.fragments.main.model.CategoriesModel
import com.ivan.wallpapers.ui.fragments.main.view.MainFragment

class CategoriesAdapter (private val context: Context, private val images: ArrayList<CategoriesModel>,val activity: MainActivity): RecyclerView.Adapter<CategoriesAdapter.PhotoHolder>()  {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoHolder {
        val binding = CategoriesItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return PhotoHolder(binding)
    }
   inner class PhotoHolder(private val binding: CategoriesItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(categoryName: CategoriesModel){
            binding.tvCategoryName.text = categoryName.category
            binding.clCategoriesItem.setOnClickListener {
                MainApplication.albumId = categoryName.albumId
                MainApplication.albumName = categoryName.category
                val id = activity.navController?.currentDestination?.id
                activity.navController?.popBackStack(id!!,true)
                id?.let { it1 -> activity.navController?.navigate(it1) }
                MainApplication.mainFragmentLoader.postValue(true)
                activity.binding.drawer.closeDrawers()

            }
        }
    }
    private fun refreshCurrentFragment(){

    }
    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val foodItem = images[position]
        holder.bind(foodItem)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}