package com.ivan.wallpapers.ui.fragments.fullscreenwallpaper.view

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.ivan.wallpapers.MainActivity
import com.ivan.wallpapers.MainApplication
import com.ivan.wallpapers.R
import com.ivan.wallpapers.databinding.FragmentFullScreenWallpaperBinding
import com.ivan.wallpapers.dry.NavigationHelper
import com.ivan.wallpapers.dry.OnSwipeTouchListener
import com.ivan.wallpapers.dry.Screens
import com.ivan.wallpapers.dry.admob.mInterstitialAd
import com.ivan.wallpapers.ui.fragments.fullscreenwallpaper.viewmodel.FullScreenWallpaperViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class FullScreenWallpaper(var backTo:FragmentScreen) : Fragment(){
    private var binding: FragmentFullScreenWallpaperBinding? = null
    private val viewModel: FullScreenWallpaperViewModel by viewModels()
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
    var favouritesObserver:Observer<Boolean>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFullScreenWallpaperBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     //   viewModel = ViewModelProvider(this)[FullScreenWallpaperViewModel::class.java]
        loadWallpaper()
        observeInter()
        initSelectDialog()
        installed()
        initClicks()
        backToMainFragment()
        initFavouritesObserver()
        firebaseAnalytics = context?.let { FirebaseAnalytics.getInstance(it) }
      //  (activity as MainActivity).binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

    }
    private fun changeFavouriteColor(
        isChecked:Boolean,
        favouriteImage: ImageView?
    )
    {
            if(isChecked){
                favouriteImage?.setImageDrawable(
                    context?.let
                    {
                        ContextCompat.getDrawable(it,
                            R.drawable.ic_baseline_favorite_24)
                    })
            }
            else{
                favouriteImage?.setImageDrawable(
                    context?.let
                    {
                        ContextCompat.getDrawable(it,
                            R.drawable.ic_baseline_favorite_border_24)
                    })
            }

    }
    private fun initFavouritesObserver(){
        favouritesObserver = Observer {
            CoroutineScope(Dispatchers.Main).launch {
                changeFavouriteColor(it, binding?.ivFavourites)
            }
        }
        activity?.let { viewModel.favouritesLiveData.observe(it, favouritesObserver!!) }
    }

    private fun initClicks(){
        binding?.ivFullScreenWallpaper?.setOnClickListener {
            binding?.clSelectToInstall?.let {
                if (it.isVisible){
                    it.isVisible = false
                }
            }

        }

        binding?.ivFavourites?.setOnClickListener {
            viewModel.checkFavourites(save = true, check = false)
        }

        binding?.clSelectToInstall?.setOnClickListener {

        }

        binding?.mbtnApply?.setOnClickListener {
            showLoadingDialog()

        }

        binding?.tvInstall?.setOnClickListener {
            install()
        }

        binding?.tvSave?.setOnClickListener {
            save()
        }
    }

    private fun backToMainFragment(){
        MainApplication.fullScreenBack.postValue(true)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding?.clSelectToInstall?.let {
                    if (it.isVisible){
                        it.isVisible = false
                    }
                    else{
                        NavigationHelper.backTo(backTo)
                    }
                }

            }
        })
        context?.let { binding?.ivFullScreenWallpaper?.setOnTouchListener(object :OnSwipeTouchListener(it){
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
                NavigationHelper.backTo(backTo)
            }

            override fun onSwipeBottom() {
                super.onSwipeBottom()
                NavigationHelper.backTo(backTo)
            }
        }) }
    }

    private fun changeToSelected(
        v1Visible:View?,
        v2Visible:View?,
        v3InVisible:View?,
        v4Invisible:View?,
        textView: TextView?,
        textView1: TextView?,
        textView2: TextView?)
    {
        v1Visible?.visibility = View.VISIBLE
        v2Visible?.visibility = View.VISIBLE
        v3InVisible?.visibility = View.INVISIBLE
        v4Invisible?.visibility = View.INVISIBLE
        textView?.setTextColor(MainApplication.INSTANCE.getColor(R.color.selectTextColor))
        textView1?.setTextColor(MainApplication.INSTANCE.getColor(R.color.white))
        textView2?.setTextColor(MainApplication.INSTANCE.getColor(R.color.white))
    }

    private fun initSelectDialog(){
        binding?.tvHomeScreen?.setOnClickListener {
            homeScreen = true
            blockScreen = false
            everyScreen = false
            MainApplication.type = MainApplication.homeScreen
            changeToSelected(binding?.vTop,
                binding?.vHomeScreenBottom,
                binding?.vHomeScreenBottomBottom,
                binding?.vEveryWhere,
                binding?.tvHomeScreen,
                binding?.tvBlockScreen,
                binding?.tvEveryWhere
            )
        }
        binding?.tvBlockScreen?.setOnClickListener {
            homeScreen = false
            blockScreen = true
            everyScreen = false
            MainApplication.type = MainApplication.lockScreen
            changeToSelected(binding?.vHomeScreenBottom,
                binding?.vHomeScreenBottomBottom,
                binding?.vTop,
                binding?.vEveryWhere,
                binding?.tvBlockScreen,
                binding?.tvHomeScreen,
                binding?.tvEveryWhere
            )
        }
        binding?.tvEveryWhere?.setOnClickListener {
            homeScreen = false
            blockScreen = false
            everyScreen = true
            MainApplication.type = MainApplication.both
            changeToSelected(binding?.vHomeScreenBottomBottom,
                binding?.vEveryWhere,
                binding?.vTop,
                binding?.vHomeScreenBottom,
                binding?.tvEveryWhere,
                binding?.tvHomeScreen,
                binding?.tvBlockScreen
            )
        }
    }

    private fun save(){
        CoroutineScope(Dispatchers.Main).launch {
            (activity as MainActivity).showAdmob()
        }
        NavigationHelper.navigate(Screens.installDialog())
        snackbarText = "save"
        CoroutineScope(Dispatchers.Default).launch {
            withContext(Dispatchers.IO){
                imageUrl?.let { wallpaper = viewModel.mLoad(it) }
                context?.let {
                    viewModel.mSaveMediaToStorage(wallpaper,
                        it, wallpaperId)
                }
            }
        }
        val params = Bundle()
        params.putString("save", "saved")
        firebaseAnalytics?.logEvent("save", params)
//        wallpaper?.let { it1 -> viewModel.saveToInternalStorage(it1,System.currentTimeMillis().toString()+"wallpaper"+wallpaperId+".png", requireContext() ) }.also {
//        }
    }
    private fun observeInter(){
        interObserver = Observer{
            //showInter()
        }
        viewModel.finished.observe(requireActivity(), interObserver)
    }
    private fun showLoadingDialog(){
        admobCallback()
        CoroutineScope(Dispatchers.Main).launch {
            (activity as MainActivity).showAdmob()
        }
        snackbarText = "installandsave"
        binding?.clSelectToInstall?.isVisible = false
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            checkWallpaperInstallType(MainApplication.type)
        }
    }

    private fun install(){
        binding?.clSelectToInstall?.isVisible = true
    }

    private fun installed() {
        installObserver = Observer{
            checkWallpaperInstallType(it)
        }
        MainApplication.installWallpaper.observe(requireActivity(), installObserver)
    }

    private fun loadWallpaper(){
        viewModel.makeOrigPhotoRequest(MainApplication.INSTANCE.getString(R.string.access_token),
        MainApplication.photoId
            )
        viewModel.makeFirebaseOrigPhotoRequest(MainApplication.albumId,
            MainApplication.photoName.split("_510x900")[0])
       // viewModel.makeOrigPhotoRequest(MainApplication.INSTANCE.getString(R.string.access_token), MainApplication.photoId)
        activity?.let {
            wallpaperLoadedObserver = Observer {imageUrl->
                MainApplication.fullUrl = imageUrl
                viewModel.checkFavourites(save = false, check = true)
                CoroutineScope(Dispatchers.IO).launch {
                    imageUrl?.let { wallpaper = viewModel.mLoad(it) }
                }
                Picasso.get().load(imageUrl).centerCrop().fit().into(binding?.ivFullScreenWallpaper, object :Callback{
                    override fun onSuccess() {
                        binding?.skvLoadingFullScreen?.isVisible = false
                        MainApplication.wallpaper = binding?.ivFullScreenWallpaper?.drawable?.toBitmap()
                    }
                    override fun onError(e: Exception?) {
                    }
                })
            }
            viewModel.fullScreenWallpaper.observe(it, wallpaperLoadedObserver)
        }
        wallpaperId = "wallpaper"
        this. imageUrl = MainApplication.photoId
    }

    private fun admobCallback(){
        mInterstitialAd?.let {
         it.fullScreenContentCallback = object: FullScreenContentCallback() {
             override fun onAdClicked() {

             }

             override fun onAdDismissedFullScreenContent() {
                 installedAndSaved(MainApplication.type)
                 mInterstitialAd = null
             }

             override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                 mInterstitialAd = null
                 installedAndSaved(MainApplication.type)
             }

             override fun onAdImpression() {
             }

             override fun onAdShowedFullScreenContent() {

             }
         }
        }?:
        installedAndSaved(MainApplication.type)

    }

    private fun installedAndSaved(type: String){
        if(type=="installandsave"){
            binding?.clFullScreen?.let { Snackbar.make(it, MainApplication.INSTANCE.getString(R.string.installedandsaved),Snackbar.LENGTH_LONG).show() }
        }
        if(type == "save"){
            binding?.clFullScreen?.let { Snackbar.make(it, MainApplication.INSTANCE.getString(R.string.saved),Snackbar.LENGTH_LONG).show() }
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
                    System.currentTimeMillis().toString()+wallpaperId )
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
                    System.currentTimeMillis().toString()+wallpaperId )
                viewModel.installLockScreenWallpaper(wallpaperBitmap,
                    requireActivity())
            } }
        }
        if (type == MainApplication.homeScreen){
            wallpaper?.let {wallpaperBitmap-> context?.let { mContext ->
                viewModel.mSaveMediaToStorage(wallpaperBitmap,
                    mContext,
                    System.currentTimeMillis().toString()+wallpaperId )
                viewModel.installWallpaper(wallpaperBitmap,requireActivity())
            } }
        }
    }
    override fun onDestroyView() {
        viewModel.finished.removeObserver(interObserver)
        viewModel.fullScreenWallpaper.removeObserver(wallpaperLoadedObserver)
        (requireActivity() as MainActivity).binding.bnvWallpaperSettings.isVisible = true
        //    (activity as MainActivity).binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        binding = null
        super.onDestroyView()

    }

    override fun onStop() {
        super.onStop()
    }
}