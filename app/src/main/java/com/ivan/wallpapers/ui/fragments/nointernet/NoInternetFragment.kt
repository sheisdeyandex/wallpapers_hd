package com.ivan.wallpapers.ui.fragments.nointernet

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.ivan.wallpapers.MainActivity
import com.ivan.wallpapers.MainApplication
import com.ivan.wallpapers.databinding.FragmentNoInternetBinding
import com.ivan.wallpapers.dry.NavigationHelper
import com.ivan.wallpapers.dry.Screens

class NoInternetFragment : Fragment() {
    private var _binding: FragmentNoInternetBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NoInternetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoInternetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(NoInternetViewModel::class.java)
        (requireActivity() as MainActivity).binding.bnvWallpaperSettings.isVisible = false
        (requireActivity() as MainActivity).binding.mToolbar.isVisible = false
        binding.mbtnOk.setOnClickListener {
            MainApplication.mainFragmentLoader.postValue(true)
            NavigationHelper.navigate(Screens.main())
        }
    }

}