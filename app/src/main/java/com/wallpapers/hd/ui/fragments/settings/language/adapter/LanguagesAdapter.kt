package com.wallpapers.hd.ui.fragments.settings.language.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.ConfigurationCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wallpapers.hd.MainActivity
import com.wallpapers.hd.databinding.LanguageItemBinding
import com.wallpapers.hd.ui.fragments.settings.language.model.LanguagesModel

class LanguagesAdapter(private val context: Context, var languageFragment: MainActivity):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val differCallback = object : DiffUtil.ItemCallback<LanguagesModel>() {
        override fun areItemsTheSame(oldItem: LanguagesModel, newItem: LanguagesModel): Boolean {
            return oldItem.language == newItem.language
        }
        override fun areContentsTheSame(oldItem: LanguagesModel, newItem: LanguagesModel): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding = LanguageItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return MainViewHolder(binding)
    }
    var i  = 0
    inner class MainViewHolder(private val binding: LanguageItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(languagesModel: LanguagesModel) {
            binding.tvLanguage.text = languagesModel.language
            binding.clLanguages.setOnClickListener {
                languageFragment.setLanguage(languagesModel.languageCode)
              //  languagesFragment.setLanguage(languagesModel.languageCode)
            }
            val current = ConfigurationCompat.getLocales(context.resources.configuration).get(0)
            if (languageFragment.getCurrentLanguage().language.lowercase()
                == languagesModel.languageCode||languageFragment.getCurrentLanguage().language.lowercase()==""&&languagesModel.languageCode=="en"){
                i++
                binding.tvRadio.isChecked = true
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
