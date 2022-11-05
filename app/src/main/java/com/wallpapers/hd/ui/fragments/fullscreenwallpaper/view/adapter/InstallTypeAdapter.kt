package com.wallpapers.hd.ui.fragments.fullscreenwallpaper.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wallpapers.hd.MainApplication
import com.wallpapers.hd.R
import com.wallpapers.hd.databinding.SelectInstallTypeItemBinding

class InstallTypeAdapter (private val context: Context):
RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val differCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)
    var homeScreen = false
    var blockScreen = false
    var everyScreen = false
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding = SelectInstallTypeItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return MainViewHolder(binding)
    }
    inner class MainViewHolder(val binding: SelectInstallTypeItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(sizesModel: String) {
            binding.tvType.setTextColor(MainApplication.INSTANCE.getColor(R.color.white))
            binding.clSelect.setOnClickListener {
                if (adapterPosition==0){
                    homeScreen = true
                    blockScreen = false
                    everyScreen = false
                    binding.vBottom.isVisible = true
                    binding.vTop.isVisible = true
                    binding.tvType.setTextColor(MainApplication.INSTANCE.getColor(R.color.selectTextColor))
                }
                if (adapterPosition==1){
                    homeScreen = true
                    blockScreen = false
                    everyScreen = false
                    binding.vBottom.isVisible = true
                    binding.vTop.isVisible = true
                    binding.tvType.setTextColor(MainApplication.INSTANCE.getColor(R.color.selectTextColor))
                }
                if (adapterPosition==2){
                    homeScreen = true
                    blockScreen = false
                    everyScreen = false
                    binding.vBottom.isVisible = true
                    binding.vTop.isVisible = true
                    binding.tvType.setTextColor(MainApplication.INSTANCE.getColor(R.color.selectTextColor))
                }
            }
            binding.tvType.text = sizesModel
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