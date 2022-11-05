package com.wallpapers.hd

import android.os.Build
import android.os.Bundle
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.PurchaseInfo
import com.anjlab.android.iab.v3.SkuDetails
import com.google.android.gms.ads.MobileAds
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.firebase.analytics.FirebaseAnalytics
import com.wallpapers.hd.databinding.ActivityMainBinding
import com.wallpapers.hd.dry.*
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
    private var bp:BillingProcessor? = null
    var favourites = false
    var monthPrice = MutableLiveData<String>()
    var alwaysPrice = MutableLiveData<String>()

    var firebaseAnalytics:FirebaseAnalytics? = null
    private var navController:NavController? = null
    @Inject
    lateinit var categories:Categories

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Wallpapers)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.clMain) as NavHostFragment
        navController = navHostFragment.navController
        MobileAds.initialize(this) {}
        admob.load(applicationContext)
        initBp()
        initLeftMenuClicks()
        initBottomNavClicks()
        initStatusBar()
        initViewVisibilities()
        firebaseAnalytics =  FirebaseAnalytics.getInstance(applicationContext)

       // binding.ivCookies.setColorFilter(ContextCompat.getColor(this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY)
    }
    fun showAdmob(){
        admob.show(this)
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
        bp?.release()
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
                bp?.getPurchaseListingDetailsAsync("one_month",object :BillingProcessor.ISkuDetailsResponseListener{
                    override fun onSkuDetailsResponse(products: MutableList<SkuDetails>?) {
                        products?.forEach {skuDetails->
                            monthPrice.postValue(skuDetails.priceText)
                        }

                    }

                    override fun onSkuDetailsError(error: String?) {

                    }

                })
                bp?.getPurchaseListingDetailsAsync("full_version",object :BillingProcessor.ISkuDetailsResponseListener{
                    override fun onSkuDetailsResponse(products: MutableList<SkuDetails>?) {
                        products?.forEach {skuDetails->
                            alwaysPrice.postValue(skuDetails.priceText)
                        }

                    }

                    override fun onSkuDetailsError(error: String?) {

                    }

                })
            }

        })
        bp?.initialize()
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
//        binding.tvShare.setOnClickListener {
//            Share.share(applicationContext)
//        }
//        binding.tvUserAgreement.setOnClickListener {
//            StartChromeTab.openBrowser(getString(R.string.user_agreement_url), applicationContext)
//        }
//        binding.tvClearCookies.setOnClickListener {
//            binding.drawer.closeDrawers()
//            NavigationHelper.navigate(Screens.cacheDialog())
//
//        }
//        binding.tvLanguage.setOnClickListener {
//            NavigationHelper.navigate(Screens.language())
//        }
//        binding.tvDisableAds.setOnClickListener {
//            NavigationHelper.navigate(Screens.disableAds())
//            binding.drawer.closeDrawers()
//        }
//        binding.tvRateApp.setOnClickListener {
//            rateApp()
//        }
    }
    private fun initBottomNavClicks(){
        navController?.let { binding.bnvWallpaperSettings.setupWithNavController(it) }
    }
}