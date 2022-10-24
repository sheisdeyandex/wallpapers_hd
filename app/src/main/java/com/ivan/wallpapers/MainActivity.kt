package com.ivan.wallpapers

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.PurchaseInfo
import com.anjlab.android.iab.v3.SkuDetails
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.gms.ads.MobileAds
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.firebase.analytics.FirebaseAnalytics
import com.ivan.wallpapers.databinding.ActivityMainBinding
import com.ivan.wallpapers.dry.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : LocalizationActivity() {
    override fun onResume() {
        super.onResume()
        binding.bnvWallpaperSettings.clearAnimation()
        binding.bnvWallpaperSettings.animate().translationY(binding.bnvWallpaperSettings.height.toFloat()).duration =
            200
    }
    lateinit var binding: ActivityMainBinding
    private val navigator = AppNavigator(this, R.id.clMainMain)

    lateinit var bp:BillingProcessor
    var favourites = false
    var monthPrice = MutableLiveData<String>()
    var alwaysPrice = MutableLiveData<String>()
    private var manager:ReviewManager? = null
    var firebaseAnalytics:FirebaseAnalytics? = null
    @Inject
    lateinit var categories:Categories
    override fun onPause() {
        super.onPause()
        MainApplication.INSTANCE.navigatorHolder.removeNavigator()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        MainApplication.INSTANCE.navigatorHolder.setNavigator(navigator)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Wallpapers)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MobileAds.initialize(this) {}
        admob.load(applicationContext)
        initBp()
        initLeftMenuClicks()
        initBottomNavClicks()
        initStatusBar()
        setSideMenuAreaRatio(0.2)
        initRateApp()
        NavigationHelper.navigate(Screens.categories())
        initViewVisibilities()
        firebaseAnalytics =  FirebaseAnalytics.getInstance(applicationContext)
        binding.ivCookies.setColorFilter(ContextCompat.getColor(this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY)
    }
    fun showAdmob(){
        admob.show(this)
    }
    private fun initRateApp(){
        manager = ReviewManagerFactory.create(applicationContext)
    }
    private fun rateApp(){
        val request = manager?.requestReviewFlow()
        request?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                val flow = manager?.launchReviewFlow(this, reviewInfo)
                flow?.addOnCompleteListener { _ ->

                }
            }
        }

    }
    private fun setSideMenuAreaRatio(ration: Double) {
        val displaySize = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val display = this.display
            display?.getRealMetrics(displaySize)
        } else {
            @Suppress("DEPRECATION")
            val display = this.windowManager.defaultDisplay
            @Suppress("DEPRECATION")
            display.getMetrics(displaySize)
        }
        binding.drawer.swipeEdgeSize = (displaySize.widthPixels * ration).toInt()
    }
    private fun initViewVisibilities(){
        MainApplication.mainFragmentLiveData.observe(this){
            binding.mToolbar.title = MainApplication.albumName
            binding.mToolbar.isVisible = true
        }
        MainApplication.fullScreenBack.observe(this){

            binding.bnvWallpaperSettings.isVisible = false
            binding.mToolbar.isVisible = false
        }
        MainApplication.mainFragmentLoader.observe(this){

            binding.bnvWallpaperSettings.isVisible = true
        }

    }
    override fun onDestroy() {
        bp.release()
        super.onDestroy()
    }
    private fun initBp(){
        bp = BillingProcessor(this, "", object :BillingProcessor.IBillingHandler{
            override fun onProductPurchased(productId: String, details: PurchaseInfo?) {
                val params = Bundle()
                params.putString("done", "yes")
                firebaseAnalytics?.logEvent("app_purchase", params)
            }

            override fun onPurchaseHistoryRestored() {

            }

            override fun onBillingError(errorCode: Int, error: Throwable?) {

            }

            override fun onBillingInitialized() {
                bp.getPurchaseListingDetailsAsync("one_month",object :BillingProcessor.ISkuDetailsResponseListener{
                    override fun onSkuDetailsResponse(products: MutableList<SkuDetails>?) {
                        products?.forEach {skuDetails->
                            monthPrice.postValue(skuDetails.priceText)
                        }?:Log.d("suka","error")

                    }

                    override fun onSkuDetailsError(error: String?) {
                        error?.let { Log.d("suka", it) }
                    }

                })
                bp.getPurchaseListingDetailsAsync("full_version",object :BillingProcessor.ISkuDetailsResponseListener{
                    override fun onSkuDetailsResponse(products: MutableList<SkuDetails>?) {
                        products?.forEach {skuDetails->
                            alwaysPrice.postValue(skuDetails.priceText)
                        }?:Log.d("suka","error")

                    }

                    override fun onSkuDetailsError(error: String?) {
                        error?.let { Log.d("suka", it) }
                    }

                })
            }

        })
        bp.initialize()
    }
    private fun initStatusBar(){
        makeStatusBarTransparent()
        ViewCompat.setOnApplyWindowInsetsListener(binding.clMainMain) { _, insets ->
            binding.vTop.setMarginTop(insets.systemWindowInsetTop)
            binding.vTop.setMarginTop(insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS)
        }
    }
    private fun initLeftMenuClicks(){
        binding.tvShare.setOnClickListener {
            Share.share(applicationContext)
        }
        binding.tvUserAgreement.setOnClickListener {
            StartChromeTab.openBrowser(getString(R.string.user_agreement_url), applicationContext)
        }
        binding.tvClearCookies.setOnClickListener {
            binding.drawer.closeDrawers()
            NavigationHelper.navigate(Screens.cacheDialog())

        }
        binding.tvLanguage.setOnClickListener {
            NavigationHelper.navigate(Screens.language())
        }
        binding.tvDisableAds.setOnClickListener {
            NavigationHelper.navigate(Screens.disableAds())
            binding.drawer.closeDrawers()
        }
        binding.tvRateApp.setOnClickListener {
            rateApp()
        }
    }
    private fun initBottomNavClicks(){
        binding.bnvWallpaperSettings.setOnItemSelectedListener {
            if (it.itemId==R.id.settings){
                binding.drawer.openDrawer(GravityCompat.START)
            }
            if (it.itemId==R.id.main){
                NavigationHelper.backTo(Screens.main())
            }
            if (it.itemId==R.id.favourites){
                NavigationHelper.navigate(Screens.favourites())
                favourites = true
            }
            true
        }
    }
}