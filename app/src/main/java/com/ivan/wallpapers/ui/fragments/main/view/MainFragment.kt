package com.ivan.wallpapers.ui.fragments.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.ivan.wallpapers.MainActivity
import com.ivan.wallpapers.MainApplication
import com.ivan.wallpapers.R
import com.ivan.wallpapers.databinding.FragmentMainBinding
import com.ivan.wallpapers.ui.fragments.main.model.mainmodel.SizesModel
import com.ivan.wallpapers.ui.fragments.main.view.adapter.MainAdapter
import com.ivan.wallpapers.ui.fragments.main.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    lateinit var mainAdapter: MainAdapter
    private val binding get() = _binding!!
    lateinit var observer: Observer<Boolean>
    private lateinit var viewModel: MainViewModel
    var firebaseAnalytics: FirebaseAnalytics? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        initCategory()
        initRecycler()
        initList()
        firebaseAnalytics = context?.let { FirebaseAnalytics.getInstance(it) }
        sentCategoryAnal()
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
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.makeRequest(MainApplication.albumId)
        }
    }
    private fun initList(){
        observer = Observer {
            if (it){
                val newList = ArrayList<SizesModel>()
                newList.addAll(viewModel.sizeModelList)
                mainAdapter.differ.submitList(newList)
                (requireActivity() as MainActivity).binding.skvLoadingContent.isVisible = false
                binding.skvLoadingEnd.isVisible = false
            }
            else{
                findNavController().navigate(R.id.action_mainFragment_to_noInternetFragment)
            }

        }
        viewModel.list.observe(requireActivity(), observer)
    }
    private fun RecyclerView.addOnScrolledToEnd(onScrolledToEnd: () -> Unit){

        this.addOnScrollListener(object: RecyclerView.OnScrollListener(){

            private val VISIBLE_THRESHOLD = 5

            private var loading = true
            private var previousTotal = 0

            override fun onScrollStateChanged(recyclerView: RecyclerView,
                                              newState: Int) {

                with(layoutManager as GridLayoutManager){

                    val visibleItemCount = childCount
                    val totalItemCount = itemCount
                    val firstVisibleItem = findFirstVisibleItemPosition()

                    if (loading && totalItemCount > previousTotal){

                        loading = false
                        previousTotal = totalItemCount
                    }

                    if(!loading && (totalItemCount - visibleItemCount-7) <= (firstVisibleItem + VISIBLE_THRESHOLD)){
                        onScrolledToEnd()
                        loading = true
                    }
                }
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.list.removeObserver(observer)
    }
    private fun initRecycler(){
        val gridLayoutManager = GridLayoutManager(context,3)
        binding.rvWallpapers.layoutManager = gridLayoutManager
        mainAdapter = MainAdapter(requireContext(), this)
        binding.rvWallpapers.addOnScrolledToEnd {
            binding.skvLoadingEnd.isVisible = true
            initCategory()
        }
    //    binding.rvWallpapers.addItemDecoration(GridDividerItemDecoration(10))
        binding.rvWallpapers.adapter = mainAdapter
    }
}