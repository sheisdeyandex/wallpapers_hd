 package com.ivan.wallpapers.ui.fragments.settings.removeads

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.SkuDetails
import com.ivan.wallpapers.MainActivity
import com.ivan.wallpapers.R
import com.ivan.wallpapers.databinding.FragmentRemoveAdsBinding
import com.ivan.wallpapers.ui.fragments.main.view.MainFragment

 class RemoveAdsFragment : Fragment() {
    private var _binding: FragmentRemoveAdsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RemoveAdsViewModel

     override fun onCreateView(
         inflater: LayoutInflater, container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View {
         _binding = FragmentRemoveAdsBinding.inflate(inflater, container, false)
         return binding.root
     }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[RemoveAdsViewModel::class.java]
        (requireActivity() as MainActivity).binding.bnvWallpaperSettings.isVisible = false
        initClicks()
        initSubscription()
        (requireActivity() as MainActivity).binding.mToolbar.isVisible = false
    }
     private fun initClicks(){
         binding.ivBack.setOnClickListener {
             view?.findNavController()?.popBackStack()
         }
         binding.mcvMonth.setOnClickListener {
             context?.getColor(R.color.selectTextColor)?.let { yellowColor -> binding.mcvMonth.strokeColor = yellowColor }
             context?.getColor(R.color.selectTextColor)?.let { yellowColor -> binding.mcvMonthText.setCardBackgroundColor(yellowColor) }
             context?.getColor(R.color.white)?.let { whiteColor -> binding.mcvAlways.strokeColor = whiteColor }
             context?.getColor(R.color.white)?.let { whiteColor -> binding.mcvAlwaysText.setCardBackgroundColor(whiteColor) }
         }
         binding.mcvAlways.setOnClickListener {
             context?.getColor(R.color.selectTextColor)?.let { yellowColor -> binding.mcvAlways.strokeColor = yellowColor }
             context?.getColor(R.color.selectTextColor)?.let { yellowColor -> binding.mcvAlwaysText.setCardBackgroundColor(yellowColor) }
             context?.getColor(R.color.white)?.let { whiteColor -> binding.mcvMonth.strokeColor = whiteColor }
             context?.getColor(R.color.white)?.let { whiteColor -> binding.mcvMonthText.setCardBackgroundColor(whiteColor)}
         }
     }
     private fun initSubscription(){
         (requireActivity() as MainActivity).monthPrice.observe(requireActivity()) {
             val priceText = it+"\n"+getString(R.string.month)
             binding.tvMonth.text = it
         }
         (requireActivity() as MainActivity).alwaysPrice.observe(requireActivity()) {
             val priceText = it+"\n"+getString(R.string.always)
             binding.tvAlways.text = it
         }
     }
     override fun onDestroyView() {
         super.onDestroyView()
         _binding = null
         (requireActivity() as MainActivity).binding.bnvWallpaperSettings.isVisible = true
     }
}