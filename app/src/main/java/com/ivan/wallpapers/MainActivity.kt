package com.ivan.wallpapers

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.PurchaseInfo
import com.anjlab.android.iab.v3.SkuDetails
import com.appodeal.ads.Appodeal
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.firebase.analytics.FirebaseAnalytics
import com.ivan.wallpapers.adapter.CategoriesAdapter
import com.ivan.wallpapers.databinding.ActivityMainBinding
import com.ivan.wallpapers.dry.NavigationHelper
import com.ivan.wallpapers.dry.makeStatusBarTransparent
import com.ivan.wallpapers.dry.setMarginTop
import com.ivan.wallpapers.ui.fragments.main.model.CategoriesModel
import java.io.File
import java.lang.Math.log10
import java.text.DecimalFormat
import kotlin.math.pow


class MainActivity : LocalizationActivity() {
    override fun onResume() {
        super.onResume()
        binding.bnvWallpaperSettings.clearAnimation()
        binding.bnvWallpaperSettings.animate().translationY(binding.bnvWallpaperSettings.height.toFloat()).duration =
            200
    }

    lateinit var binding: ActivityMainBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    lateinit var bp:BillingProcessor
    var monthPrice = MutableLiveData<String>()
    var alwaysPrice = MutableLiveData<String>()
    var navController:NavController? = null
    var manager:ReviewManager? = null
    var firebaseAnalytics:FirebaseAnalytics? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Wallpapers)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavController()
        initBp()
        initLeftMenuClicks()
        initBottomNavClicks()
        initRecycler()
        initAppodeal()
        initStatusBar()
        initViewVisibilities()
        initCategoryAtStart()
        setSideMenuAreaRatio(0.2)
        initRateApp()
        firebaseAnalytics =  FirebaseAnalytics.getInstance(applicationContext)
        binding.ivCookies.setColorFilter(ContextCompat.getColor(this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY)

        //initCategoryAtStart()
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
            } else {

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
            binding.skvLoadingContent.isVisible = false
            binding.bnvWallpaperSettings.isVisible = false
            binding.mToolbar.isVisible = false
        }
        MainApplication.mainFragmentLoader.observe(this){
            binding.skvLoadingContent.isVisible = true
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
    private fun initNavController(){
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.clMain) as NavHostFragment
        navController = navHostFragment.navController
    }
    private fun initCategoryAtStart(){
        MainApplication.albumId = getString(R.string.newest_album_id)
        MainApplication.albumName = getString(R.string.category_NEWEST)
    }
    private fun startChromeTabs(){
        val url = getString(R.string.user_agreement_url)
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
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
            share()
        }
        binding.tvUserAgreement.setOnClickListener {
            startChromeTabs()
        }
        binding.tvClearCookies.setOnClickListener {
            binding.drawer.closeDrawers()
            navController?.navigate(R.id.action_mainFragment_to_dialogCache)

        }
        binding.tvLanguage.setOnClickListener {
            replaceFragment(R.id.action_mainFragment_to_languagesFragment)
        }
        binding.tvDisableAds.setOnClickListener {
            replaceFragment(R.id.action_mainFragment_to_removeAdsFragment)
            binding.drawer.closeDrawers()
        }
        binding.tvRateApp.setOnClickListener {
            rateApp()
        }
    }
    fun replaceFragment(fragment: Int){
        navController?.let { NavigationHelper.navigate(fragment, it) }
    }
    private fun initAppodeal(){
        Appodeal.initialize(this, getString(R.string.appodeal_app_key), Appodeal.INTERSTITIAL, true)
        Appodeal.setTesting(true)
     //   Appodeal.setAutoCache(Appodeal.INTERSTITIAL, false)
//        Appodeal.initialize(this, getString(R.string.appodeal_app_key), Appodeal.INTERSTITIAL, object :
//            ApdInitializationCallback {
//            override fun onInitializationFinished(list: List<ApdInitializationError>?) {
//                Appodeal.setTesting(true)
//                list?.forEach {
//                    Log.d("sukaappodealerrorinit", it.localizedMessage)
//                }
//                Log.d("sukainited", "it.localizedMessage")
//
//            }
//
//        })
    }
    private fun share(){
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
            var shareMessage = getString(R.string.share_text)
            shareMessage =
                """
                ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                
                
                """.trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share)))
        } catch (e: Exception) {
            //e.toString();
        }
    }
    private fun initBottomNavClicks(){
        binding.bnvWallpaperSettings.setOnItemSelectedListener {
            if (it.itemId==R.id.settings){
                binding.drawer.openDrawer(GravityCompat.START)
            }
            if (it.itemId==R.id.right_menu){
                binding.drawer.openDrawer(GravityCompat.END)
            }
            true
        }
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        addCategoriesList()
        initCategoryAtStart()
    }
    private fun addCategoriesList():ArrayList<CategoriesModel>{
        val butts = arrayOf(
            MainApplication.INSTANCE.getString(R.string.category_NEWEST),
            MainApplication.INSTANCE.getString(R.string.category_3D),
            MainApplication.INSTANCE.getString(R.string.category_AMOLED),
            MainApplication.INSTANCE.getString(R.string.category_DOUBLE),
            MainApplication.INSTANCE.getString(R.string.category_ABSTRACTION),
            MainApplication.INSTANCE.getString(R.string.category_AUTO),
            MainApplication.INSTANCE.getString(R.string.category_ANIME),
            MainApplication.INSTANCE.getString(R.string.category_ARMY),
            MainApplication.INSTANCE.getString(R.string.category_ART),
            MainApplication.INSTANCE.getString(R.string.category_ARCHITECTURE),
            MainApplication.INSTANCE.getString(R.string.category_FOOD),
            MainApplication.INSTANCE.getString(R.string.category_ANIMALS),
            MainApplication.INSTANCE.getString(R.string.category_ZNAKI),
            MainApplication.INSTANCE.getString(R.string.category_GAMES),
            MainApplication.INSTANCE.getString(R.string.category_SPACE),
            MainApplication.INSTANCE.getString(R.string.category_PIPL),
            MainApplication.INSTANCE.getString(R.string.category_MACRO),
            MainApplication.INSTANCE.getString(R.string.category_MINIMALISM),
            MainApplication.INSTANCE.getString(R.string.category_MOTO),
            MainApplication.INSTANCE.getString(R.string.category_MUSIC),
            MainApplication.INSTANCE.getString(R.string.category_HOLIDAYS),
            MainApplication.INSTANCE.getString(R.string.category_NATURE),
            MainApplication.INSTANCE.getString(R.string.category_SPORT),
            MainApplication.INSTANCE.getString(R.string.category_SUPERHIROS),
            MainApplication.INSTANCE.getString(R.string.category_TEXTURE),
            MainApplication.INSTANCE.getString(R.string.category_FILMS),
            MainApplication.INSTANCE.getString(R.string.category_FANTASY),
            MainApplication.INSTANCE.getString(R.string.category_FLOWERS),
            MainApplication.INSTANCE.getString(R.string.category_HI_TECH),
            MainApplication.INSTANCE.getString(R.string.category_OTHER)
            )
        val categoryList = ArrayList<CategoriesModel>()
        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_NEWEST),
                MainApplication.INSTANCE.getString(
                    R.string.newest_album_id)))
        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_3D),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_3d)))
        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_AMOLED),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_amoled)))
        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_DOUBLE),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_double)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_ABSTRACTION),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_ABSTRACTION)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_AUTO),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_AUTO)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_ANIME),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_ANIME)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_ARMY),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_ARMY)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_ARCHITECTURE),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_ARCHITECTURE)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_FOOD),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_FOOD)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_ANIMALS),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_ANIMALS)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_ZNAKI),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_ZNAKI)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_GAMES),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_GAMES)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_SPACE),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_SPACE)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_PIPL),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_PIPL)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_MACRO),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_MACRO)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_MINIMALISM),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_MINIMALISM)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_MOTO),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_MOTO)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_MUSIC),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_MUSIC)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_HOLIDAYS),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_HOLIDAYS)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_NATURE),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_NATURE)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_SPORT),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_SPORT)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_SUPERHIROS),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_SUPERHIROS)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_TEXTURE),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_TEXTURE)))

//        categoryList.add(
//            CategoriesModel(
//                MainApplication.INSTANCE.getString(
//                    R.string.category_HORRORS),
//                MainApplication.INSTANCE.getString(
//                    R.string.album_id_HORRORS)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_FILMS),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_FILMS)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_FANTASY),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_FANTASY)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_FLOWERS),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_FLOWERS)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_HI_TECH),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_HI_TECH)))

        categoryList.add(
            CategoriesModel(
                MainApplication.INSTANCE.getString(
                    R.string.category_OTHER),
                MainApplication.INSTANCE.getString(
                    R.string.album_id_OTHER)))
        return categoryList
    }
    private fun initRecycler(){
        binding.rvCategories.layoutManager =
            LinearLayoutManager(
                applicationContext,
                LinearLayoutManager.VERTICAL,
                false
            )
        categoriesAdapter = CategoriesAdapter(applicationContext, addCategoriesList(), this)
        binding.rvCategories.adapter = categoriesAdapter


    }
}