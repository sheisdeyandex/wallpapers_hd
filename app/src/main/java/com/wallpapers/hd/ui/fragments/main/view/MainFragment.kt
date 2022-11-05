package com.wallpapers.hd.ui.fragments.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.wallpapers.hd.MainApplication
import com.wallpapers.hd.databinding.FragmentMainBinding
import com.wallpapers.hd.dry.RecyclerViewScrollToEnd.addOnScrolledToEnd
import com.wallpapers.hd.ui.fragments.main.model.mainmodel.SizesModel
import com.wallpapers.hd.ui.fragments.main.view.adapter.MainAdapter
import com.wallpapers.hd.ui.fragments.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private var mainAdapter: MainAdapter? = null
    private val binding get() = _binding!!
    private lateinit var observer: Observer<Boolean>
    private lateinit var categoryChangeObserver: Observer<Boolean>
    private lateinit var viewModel: MainViewModel
    private var firebaseAnalytics: FirebaseAnalytics? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        categoryChangeObserver()
        initRecycler()
        initList()
        firebaseAnalytics = context?.let { FirebaseAnalytics.getInstance(it) }
        sentCategoryAnal()
    }
    private fun refreshCurrentFragment(){
        lifecycleScope.launchWhenResumed {
            val id = findNavController().currentDestination?.id
            id?.let {
                findNavController().popBackStack(it,true)
                findNavController().navigate(it)
            }
        }
        CoroutineScope(Dispatchers.Main).launch {


        }
    }
    private fun categoryChangeObserver(){
        categoryChangeObserver = Observer {
            CoroutineScope(Dispatchers.IO).launch {
                if (MainApplication.albumId!=viewModel.albumId){
                 refreshCurrentFragment()
                }
            }
        }
        activity?.let { MainApplication.mainFragmentLoader.observe(it,categoryChangeObserver) }
    }

    private fun sentCategoryAnal(){
        val params = Bundle()
        params.putString("category_name", MainApplication.albumName)
        firebaseAnalytics?.logEvent("category", params)
    }

    override fun onStart() {
        super.onStart()
        MainApplication.mainFragmentLiveData.postValue(true)
    }

    private fun initCategory(){
        CoroutineScope(Dispatchers.IO).launch{
            viewModel.makeRequest(MainApplication.albumId)
        }
    }
    private fun initList(){
        observer = Observer {
            if (it){
                val newList = ArrayList<SizesModel>()
                newList.addAll(viewModel.sizeModelList)
                mainAdapter?.differ?.submitList(newList)
                binding.skvLoadingMiddle.isVisible = false
                binding.skvLoadingEnd.isVisible = false
            }
            else{
              //  NavigationHelper.navigate(Screens.noInternet())
            }

        }
        viewModel.list.observe(requireActivity(), observer)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        viewModel.list.removeObserver(observer)
        MainApplication.mainFragmentLoader.removeObserver(categoryChangeObserver)
    }

    private fun initRecycler(){
        val gridLayoutManager = GridLayoutManager(context,3)
        binding.rvWallpapers.layoutManager = gridLayoutManager
        mainAdapter = context?.let { MainAdapter(it) }
        binding.rvWallpapers.addOnScrolledToEnd {
            binding.skvLoadingEnd.isVisible = true
            initCategory()
        }
        binding.rvWallpapers.adapter = mainAdapter
    }
}