package com.ivan.wallpapers.ui.fragments.categories

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.ivan.wallpapers.MainActivity
import com.ivan.wallpapers.R
import com.ivan.wallpapers.databinding.FragmentCategoriesBinding
import com.ivan.wallpapers.databinding.FragmentMainBinding
import com.ivan.wallpapers.dry.Categories
import com.ivan.wallpapers.dry.SpacesItemDecoration
import com.ivan.wallpapers.ui.fragments.categories.adapter.MainCategoriesAdapter
import com.ivan.wallpapers.ui.fragments.main.view.adapter.MainAdapter
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private var mainCategoriesAdapter:MainCategoriesAdapter? = null
    @Inject
    lateinit var categories: Categories
    companion object {
        fun newInstance() = CategoriesFragment()
    }

    private lateinit var viewModel: CategoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CategoriesViewModel::class.java]
        initRecycler()
        (activity as MainActivity).binding.mToolbar.isVisible = false
    }

    override fun onDestroy() {
        (activity as MainActivity).binding.mToolbar.isVisible = true
        super.onDestroy()
    }

    private fun initRecycler(){
        val gridLayoutManager = GridLayoutManager(context,2)
        binding.rvCategories.layoutManager = gridLayoutManager
        mainCategoriesAdapter = context?.let { activity?.let { it1 ->
            MainCategoriesAdapter(it,
                it1
            )
        } }
        binding.rvCategories.adapter = mainCategoriesAdapter
        binding.rvCategories.addItemDecoration(SpacesItemDecoration(resources.getDimension(R.dimen.main_recycler_decoration)
            .toInt()))
        mainCategoriesAdapter?.differ?.submitList(context?.let {
            categories.getCategoriesList(it, "alphabetOrder")
        })
    }

}