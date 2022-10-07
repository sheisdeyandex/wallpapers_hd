package com.ivan.wallpapers.ui.fragments.settings.language

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.ivan.wallpapers.MainActivity
import com.ivan.wallpapers.databinding.FragmentLanguageBinding
import com.ivan.wallpapers.ui.fragments.settings.language.adapter.LanguagesAdapter
import java.util.*

class LanguageFragment : Fragment() {

    private var _binding: FragmentLanguageBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LanguageViewModel
    private var languagesAdapter:LanguagesAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LanguageViewModel::class.java]
        initRecycler()
        addLanguages()
        (activity as MainActivity).binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        (requireActivity() as MainActivity).binding.bnvWallpaperSettings.isVisible = false
        (requireActivity() as MainActivity).binding.mToolbar.isVisible = false
    }

    override fun onDestroyView() {
        (requireActivity() as MainActivity).binding.bnvWallpaperSettings.isVisible = true
        (activity as MainActivity).binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        super.onDestroyView()
    }

    private fun addLanguages(){
        languagesAdapter?.differ?.submitList(viewModel.addLanguagesToList())
    }
//    fun setLanguage(code:String){
//
//        val config = (activity as MainActivity).baseContext.resources?.configuration
//        val locale = Locale(code)
//        Locale.setDefault(locale)
//        config?.setLocale(locale)
//        config?.setLayoutDirection(locale)
//        config?.let { (activity as MainActivity).baseContext?.createConfigurationContext(it) }
//        (activity as MainActivity).baseContext?.resources?.updateConfiguration(config, requireContext(). resources.displayMetrics)
//    }

    private fun initRecycler(){
        binding.rvLanguage.layoutManager =
            LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
        languagesAdapter = context?.let { LanguagesAdapter(it, (activity as MainActivity))}
        binding.rvLanguage.adapter = languagesAdapter

    }

}