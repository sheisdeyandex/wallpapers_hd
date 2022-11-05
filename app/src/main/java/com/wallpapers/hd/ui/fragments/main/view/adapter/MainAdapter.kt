package com.wallpapers.hd.ui.fragments.main.view.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wallpapers.hd.MainApplication
import com.wallpapers.hd.R
import com.wallpapers.hd.databinding.WallpapersItemBinding
import com.wallpapers.hd.dry.GlideMainLoader
import com.wallpapers.hd.ui.fragments.main.model.mainmodel.SizesModel
import java.util.Calendar

class MainAdapter(private val context: Context?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var glideMainLoader: GlideMainLoader? = null
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
            glideMainLoader = GlideMainLoader(context,
                sizesModel.url,
                binding.skvLoading,
                binding.ivWallpapers)
            glideMainLoader?.loadImage()
            binding.ivWallpapers.setOnClickListener {
                MainApplication.photoId = sizesModel.id
                MainApplication.thumbUrl = sizesModel.url
                MainApplication.sizedModel = SizesModel(sizesModel.type, sizesModel.url, sizesModel.id, Calendar.getInstance().time,0)
                it.findNavController().navigate(R.id.action_mainFragment_to_fullScreenFragment)
                //NavigationHelper.navigate(Screens.fullScreen(Screens.main()))
            }
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
