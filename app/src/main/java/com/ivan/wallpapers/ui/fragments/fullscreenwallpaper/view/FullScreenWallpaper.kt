package com.ivan.wallpapers.ui.fragments.fullscreenwallpaper.view

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.appodeal.ads.Appodeal
import com.appodeal.ads.InterstitialCallbacks
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.ivan.wallpapers.MainActivity
import com.ivan.wallpapers.MainApplication
import com.ivan.wallpapers.R
import com.ivan.wallpapers.databinding.FragmentFullScreenWallpaperBinding
import com.ivan.wallpapers.dry.OnSwipeTouchListener
import com.ivan.wallpapers.ui.fragments.fullscreenwallpaper.viewmodel.FullScreenWallpaperViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*


class FullScreenWallpaper : Fragment(){
    private var _binding: FragmentFullScreenWallpaperBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FullScreenWallpaperViewModel
    private lateinit var wallpaperId :String
    private lateinit var interObserver:Observer<Boolean>
    private lateinit var installObserver:Observer<String>
    private lateinit var wallpaperLoadedObserver:Observer<String>
    var homeScreen  = false
    var blockScreen = false
    var everyScreen = false
    private var imageUrl:String? = null
    var snackbarText= ""
    var wallpaper :Bitmap? = null
    var firebaseAnalytics:FirebaseAnalytics? = null
    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.finished.removeObserver(interObserver)
        viewModel.fullScreenWallpaper.removeObserver(wallpaperLoadedObserver)
        (requireActivity() as MainActivity).binding.bnvWallpaperSettings.isVisible = true
        (activity as MainActivity).binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

        _binding = null
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFullScreenWallpaperBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FullScreenWallpaperViewModel::class.java]
        loadWallpaper()
        observeInter()
        initSelectDialog()
        installed()
        initClicks()
        backToMainFragment()
        initAppodealInterCallback()
        firebaseAnalytics = context?.let { FirebaseAnalytics.getInstance(it) }
        (activity as MainActivity).binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

    }
    private fun initClicks(){
        binding.ivFullScreenWallpaper.setOnClickListener {
            if (binding.clSelectToInstall.isVisible){
                binding.clSelectToInstall.isVisible = false
            }
        }

        binding.clSelectToInstall.setOnClickListener {

        }

        binding.mbtnApply.setOnClickListener {
            showLoadingDialog()

        }

        binding.tvInstall.setOnClickListener {
            install()
        }

        binding.tvSave.setOnClickListener {
            save()
        }
    }

    private fun backToMainFragment(){
        MainApplication.fullScreenBack.postValue(true)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.clSelectToInstall.isVisible){
                    binding.clSelectToInstall.isVisible = false
                }
                else{
                    view?.findNavController()?.popBackStack()
                }
            }
        })
        val navBuilder = NavOptions.Builder()
        navBuilder.setPopExitAnim(R.anim.exit_anim_bottom)
        val navBuilderTop = NavOptions.Builder()
        navBuilder.setPopExitAnim(R.anim.exit_anim)
        context?.let { binding.ivFullScreenWallpaper.setOnTouchListener(object :OnSwipeTouchListener(it){
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                return super.onTouch(v, event)
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
            }

            override fun onSwipeLeft() {
                super.onSwipeLeft()
            }

            override fun onSwipeTop() {
                super.onSwipeTop()

                view?.findNavController()?.popBackStack()
            }

            override fun onSwipeBottom() {
                super.onSwipeBottom()
                view?.findNavController()?.popBackStack()
            }
        }) }
    }

    private fun changeToSelected(
        v1Visible:View,
        v2Visible:View,
        v3InVisible:View,
        v4Invisible:View,
        textView: TextView,
        textView1: TextView,
        textView2: TextView)
    {
        v1Visible.visibility = View.VISIBLE
        v2Visible.visibility = View.VISIBLE
        v3InVisible.visibility = View.INVISIBLE
        v4Invisible.visibility = View.INVISIBLE
        textView.setTextColor(MainApplication.INSTANCE.getColor(R.color.selectTextColor))
        textView1.setTextColor(MainApplication.INSTANCE.getColor(R.color.white))
        textView2.setTextColor(MainApplication.INSTANCE.getColor(R.color.white))
    }

    private fun initSelectDialog(){
        binding.tvHomeScreen.setOnClickListener {
            homeScreen = true
            blockScreen = false
            everyScreen = false
            MainApplication.type = MainApplication.homeScreen
            changeToSelected(binding.vTop,
                binding.vHomeScreenBottom,
                binding.vHomeScreenBottomBottom,
                binding.vEveryWhere,
                binding.tvHomeScreen,
                binding.tvBlockScreen,
                binding.tvEveryWhere
            )
        }
        binding.tvBlockScreen.setOnClickListener {
            homeScreen = false
            blockScreen = true
            everyScreen = false
            MainApplication.type = MainApplication.lockScreen
            changeToSelected(binding.vHomeScreenBottom,
                binding.vHomeScreenBottomBottom,
                binding.vTop,
                binding.vEveryWhere,
                binding.tvBlockScreen,
                binding.tvHomeScreen,
                binding.tvEveryWhere
            )
        }
        binding.tvEveryWhere.setOnClickListener {
            homeScreen = false
            blockScreen = false
            everyScreen = true
            MainApplication.type = MainApplication.both
            changeToSelected(binding.vHomeScreenBottomBottom,
                binding.vEveryWhere,
                binding.vTop,
                binding.vHomeScreenBottom,
                binding.tvEveryWhere,
                binding.tvHomeScreen,
                binding.tvBlockScreen
            )
        }
    }

    private fun save(){
        showInter()
        view?.findNavController()?.navigate(R.id.action_fullScreenWallpaper_to_dialogInstall)
        snackbarText = "save"
        CoroutineScope(Dispatchers.Default).launch {
            withContext(Dispatchers.IO){
                imageUrl?.let { wallpaper = viewModel.mLoad(it) }
                context?.let {
                    viewModel.mSaveMediaToStorage(wallpaper,
                        it, wallpaperId+System.currentTimeMillis())
                }
            }
        }
        val params = Bundle()
        params.putString("save", "saved")
        firebaseAnalytics?.logEvent("save", params)
//        wallpaper?.let { it1 -> viewModel.saveToInternalStorage(it1,System.currentTimeMillis().toString()+"wallpaper"+wallpaperId+".png", requireContext() ) }.also {
//        }
    }

    private fun showInter(){
       // Appodeal.cache(requireActivity(), Appodeal.INTERSTITIAL)
        if (Appodeal.isLoaded(Appodeal.INTERSTITIAL)){
            activity?.let { Appodeal.show(it, Appodeal.INTERSTITIAL) }
        }
    }

    private fun observeInter(){
        interObserver = Observer{
            //showInter()
        }
        viewModel.finished.observe(requireActivity(), interObserver)
    }

    private fun showLoadingDialog(){
        showInter()
        view?.findNavController()?.navigate(R.id.action_fullScreenWallpaper_to_dialogInstall)
        snackbarText = "installandsave"
        binding.clSelectToInstall.isVisible = false
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            checkWallpaperInstallType(MainApplication.type)
        }
    }

    private fun install(){
        binding.clSelectToInstall.isVisible = true
    }

    private fun installed() {
        installObserver = Observer{
            checkWallpaperInstallType(it)
            initAppodealInterCallback()
        }
        MainApplication.installWallpaper.observe(requireActivity(), installObserver)
    }

    private fun loadWallpaper(){
        viewModel.makeOrigPhotoRequest(MainApplication.INSTANCE.getString(R.string.access_token), MainApplication.photoId)
        activity?.let {
            wallpaperLoadedObserver = Observer {imageUrl->
                wallpaperId = MainApplication.photoId
               this. imageUrl = imageUrl
                Picasso.get().load(imageUrl).centerCrop().fit().into(binding.ivFullScreenWallpaper, object :Callback{
                    override fun onSuccess() {
                        CoroutineScope(Dispatchers.IO).launch {
                            imageUrl?.let { wallpaper = viewModel.mLoad(it) }
                        }
                        binding.skvLoadingFullScreen.isVisible = false
                        MainApplication.wallpaper = binding.ivFullScreenWallpaper.drawable.toBitmap()
                    }
                    override fun onError(e: Exception?) {
                    }
                })
            }
            viewModel.fullScreenWallpaper.observe(it, wallpaperLoadedObserver)
        }
    }

    private fun initAppodealInterCallback(){
        Appodeal.setInterstitialCallbacks(object :InterstitialCallbacks{
            override fun onInterstitialClicked() {

            }

            override fun onInterstitialClosed() {
                installedAndSaved(snackbarText)
            }

            override fun onInterstitialExpired() {
            }

            override fun onInterstitialFailedToLoad() {
             //   showInter()
            }

            override fun onInterstitialLoaded(isPrecache: Boolean) {

            }

            override fun onInterstitialShowFailed() {
           //     showInter()
                view?.findNavController()?.popBackStack()
                installedAndSaved(snackbarText)
            }

            override fun onInterstitialShown() {
                val params = Bundle()
                params.putString("shown", "yes")
                firebaseAnalytics?.logEvent("appodealAd", params)
                view?.findNavController()?.popBackStack()
                MainApplication.installWallpaper.postValue("")
          //      view?.findNavController()?.popBackStack()
            }
        })
    }

    private fun installedAndSaved(type: String){
        if(type=="installandsave"){
            Snackbar.make(binding.clFullScreen, MainApplication.INSTANCE.getString(R.string.installedandsaved),Snackbar.LENGTH_LONG).show()
        }
        if(type == "save"){
            Snackbar.make(binding.clFullScreen, MainApplication.INSTANCE.getString(R.string.saved),Snackbar.LENGTH_LONG).show()

        }
     }

    private fun checkWallpaperInstallType(type:String){
        val params = Bundle()
        params.putString("install_type", type)
        firebaseAnalytics?.logEvent("install", params)
        if (type == MainApplication.both){
            wallpaper?.let {wallpaperBitmap-> context?.let { mContext ->
                viewModel.mSaveMediaToStorage(wallpaperBitmap,
                    mContext,
                    System.currentTimeMillis().toString()+"wallpaper"+wallpaperId )
                viewModel.installWallpaper(wallpaperBitmap,
                    requireActivity())
                viewModel.installLockScreenWallpaper(wallpaperBitmap,
                    requireActivity())
            }}
        }
        if (type == MainApplication.lockScreen){
            wallpaper?.let {wallpaperBitmap-> context?.let { mContext ->
                viewModel.mSaveMediaToStorage(wallpaperBitmap,
                    mContext,
                    System.currentTimeMillis().toString()+"wallpaper"+wallpaperId )
                viewModel.installLockScreenWallpaper(wallpaperBitmap,
                    requireActivity())
            } }
        }
        if (type == MainApplication.homeScreen){
            wallpaper?.let {wallpaperBitmap-> context?.let { mContext ->
                viewModel.mSaveMediaToStorage(wallpaperBitmap,
                    mContext,
                    System.currentTimeMillis().toString()+"wallpaper"+wallpaperId )
                viewModel.installWallpaper(wallpaperBitmap,requireActivity())
            } }
        }
    }


}