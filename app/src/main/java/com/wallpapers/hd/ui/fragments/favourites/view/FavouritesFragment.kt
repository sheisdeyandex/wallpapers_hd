package com.wallpapers.hd.ui.fragments.favourites.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.wallpapers.hd.databinding.FragmentFavouritesBinding
import com.wallpapers.hd.ui.fragments.favourites.viewmodel.FavouritesViewModel
import com.wallpapers.hd.ui.fragments.main.model.mainmodel.SizesModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class FavouritesFragment : Fragment() {
    private var binding:FragmentFavouritesBinding? = null

    private val viewModel: FavouritesViewModel by viewModels()

    private var mainAdapter:com.wallpapers.hd.ui.fragments.favourites.view.adapter.MainAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler(){
        val gridLayoutManager = GridLayoutManager(context,3)
        binding?.rvFavourites?.layoutManager = gridLayoutManager
        mainAdapter = com.wallpapers.hd.ui.fragments.favourites.view.adapter.MainAdapter(context)
//        binding.rvWallpapers.addOnScrolledToEnd {
//            binding.skvLoadingEnd.isVisible = true
//            initCategory()
//        }
        binding?.rvFavourites?.adapter = mainAdapter
        CoroutineScope(Dispatchers.IO).launch {
            initList()
        }
    }

    private suspend fun initList(){
        CoroutineScope(Dispatchers.Default).async {
            var favouritesList: List<SizesModel?>
            withContext(Dispatchers.IO){
                favouritesList = viewModel.getAllFavourites()
            }
            withContext(Dispatchers.Main){
                mainAdapter?.differ?.submitList(favouritesList)
            }
        }.await()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}