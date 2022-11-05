package com.wallpapers.hd.dry


import android.content.Context
import androidx.core.content.ContextCompat
import com.wallpapers.hd.MainApplication
import com.wallpapers.hd.R
import com.wallpapers.hd.ui.fragments.main.model.CategoriesModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

@Module
@InstallIn(FragmentComponent::class)
class Categories @Inject constructor() {
    @Provides
    fun getCategoriesList(@ActivityContext context:Context, sort: String):List<CategoriesModel>{
        val categoriesList = ArrayList<CategoriesModel>()
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_dusk),
            MainApplication.INSTANCE.getString(R.string.album_id_dusk),
            "https://sun9-84.userapi.com/impg/6AIhHQvbz8u0RSff5Xh519m2PMcoB1BPe0w3Nw/n2fXOYh6Njg.jpg?size=604x402&quality=95&sign=e12ed72f239929a6319b0db52f28874a&c_uniq_tag=H5naGt67AJBhznSmuNItPNal0c3_bHJETbzqVBqB9MU&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_fog),
            MainApplication.INSTANCE.getString(R.string.album_id_fog),
            "https://sun9-42.userapi.com/impg/-0H1SmQeOJp7B3LuNrl31AatG3fDQfLa7kaadw/G38tEnZtVas.jpg?size=453x604&quality=95&sign=bf2ea925f00cd6cbc803b9ee1b543baa&c_uniq_tag=7TJGZ5HEvjdV2Wif1UUkcLUuLLkq2XlPRg6Og4wniy8&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_art),
            MainApplication.INSTANCE.getString(R.string.album_id_art),
        "https://sun9-27.userapi.com/impg/8-mwxejc7kKjaOX5g4Z_Pm9VZ74ksBoZPH-N_Q/RHn071bgvV0.jpg?size=604x453&quality=95&sign=ea016a950fa0841c307eb62e8206bfc5&c_uniq_tag=sBswg6qcIsq36LJvisQQMqwwQ8YlImivHySlytsMA8g&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Loneliness),
            MainApplication.INSTANCE.getString(R.string.album_id_loneliness),
            "https://sun9-64.userapi.com/impg/ihoDw-w1zq496zT1KOCY5AlL7gViH0gx41mdig/c5Gd7Xwc2Dg.jpg?size=604x402&quality=95&sign=d8e3d994f89b39833336b29b6f684a16&c_uniq_tag=GYuA3Rkve9wsQtDAdtM29qP0XUFhtPtmyG8ZdRGf1vQ&type=album"))
//        categoriesList.add(CategoriesModel(
//            MainApplication.INSTANCE.getString(R.string.category_Human),
//            MainApplication.INSTANCE.getString(R.string.album_id_human),
//            ContextCompat.getDrawable(context, R.drawable.trans)))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Hand),
            MainApplication.INSTANCE.getString(R.string.album_id_hand),
            "https://sun9-16.userapi.com/impg/ry76G-VJSiwgJWWpEwjmwEkkYNCO3GLpbY29VA/jSnhD26yBos.jpg?size=402x604&quality=95&sign=04b618bad9c64130678326fb910c8e87&c_uniq_tag=d9TdaWEzynfTSjaebYvb-NwJffGwvzeaAvZHS14LSeQ&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_black_and_white),
            MainApplication.INSTANCE.getString(R.string.album_id_black_and_white),
            "https://sun9-62.userapi.com/impg/aLLxqpwQZzrmDGC8Blloq6Je52IqEZM7Do-M6Q/PMymqkEJYVE.jpg?size=604x453&quality=95&sign=8403e17bc15892e7c64a93dafc2fd123&c_uniq_tag=dNUU8OKtDEZ5pQPdjpVPF-JRn1JYm8ARLFpyq2vnJc4&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Retro),
            MainApplication.INSTANCE.getString(R.string.album_id_retro),
            "https://sun9-72.userapi.com/impg/I_kVw_0Q7gDd6NO8Vaop3lEMagos6BhIBbvIew/pfqbr_-fYAM.jpg?size=604x402&quality=95&sign=8782d5ab6d935300523f144813a8e479&c_uniq_tag=0cAswZ1kNRJgYi34X002Sd1w_WFcRHuogRSwpf4uJgI&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Words),
            MainApplication.INSTANCE.getString(R.string.album_id_words),
           "https://sun9-78.userapi.com/impg/NPCrgvwMXNqBpNPPD4A4BxBo7slyzOIkhB-FEQ/qfde_KrG-Gs.jpg?size=604x340&quality=95&sign=9b774e158469d91ee3d6214268a9d84a&c_uniq_tag=0y9skbHdbvf-ILMRh1TPCjHaOuaKZ9vRHIh7KasiNO8&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_3D),
            MainApplication.INSTANCE.getString(R.string.album_id_3d),
        "https://sun9-75.userapi.com/impg/xTbrC0xlY75B7rrFUR81985DNgXTpYn8ivOs6A/MmvWgOXjm7s.jpg?size=604x453&quality=95&sign=db63051fb012ac8ba246be866d8d3d01&c_uniq_tag=w2IUuf53C9ZzIs5-wOQvAQVLfWezmfJbsUlM7XiyUk4&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_MUSIC),
            MainApplication.INSTANCE.getString(R.string.album_id_music),
            "https://sun9-74.userapi.com/impg/M9GtVJ5JnuhrBVirYOJI-kSvwOzlY600QS1ZiQ/OpMQTro22wQ.jpg?size=604x403&quality=95&sign=a6bf82897977679bb6f835a84b0b93c5&c_uniq_tag=WmvAGwkBwyATvaF8DT4LEN6camvNfPY9rgJvCr1kj8o&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Couple),
            MainApplication.INSTANCE.getString(R.string.album_id_couple),
            "https://sun9-59.userapi.com/impg/4l5Mdrlv9VCcAz0y_V-Pd7vSJ8IVPeDS7wnTEg/fMppjKgDnKk.jpg?size=483x604&quality=95&sign=0aa5cda6a0a8fe85886bcb390a436fd4&c_uniq_tag=8Ts_c7gt3Epc2ZEmIcQL4nfpIKmSi1UAvNNMZQqzATY&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_FANTASY),
            MainApplication.INSTANCE.getString(R.string.album_id_fantasy),
            "https://sun9-61.userapi.com/impg/gRU0sci3WU8JgLeDyk5JUso5ifMrpryTMKj97Q/MPgfwMALHI4.jpg?size=424x604&quality=95&sign=eb46b408eaf9ff7f9b288177ac33d586&c_uniq_tag=mk6UlSxRy_yU8WM5Qo_TlPpzREUsy0o5Kj0L0LHNfbc&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Funny),
            MainApplication.INSTANCE.getString(R.string.album_id_funny),
            "https://sun9-81.userapi.com/impg/nKHwz9DojaUHq9OUIv_F_bWSP_XgUvhnwE0SJA/LeLBbSN8tg0.jpg?size=604x377&quality=95&sign=f69c4443b761f2feeb7e31180268bfd2&c_uniq_tag=cfSpBI5S8k60_gfvsFBlfPTgL1ZP5YXXenZClJR7YKk&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_black),
            MainApplication.INSTANCE.getString(R.string.album_id_black),
            "https://sun9-73.userapi.com/impg/Acr13jmx_ILE3e1i97dt06nmeH7YJldaVHwUOA/XQUn38kqzwM.jpg?size=340x604&quality=95&sign=c274ac8295e091d634de456694ead6fa&c_uniq_tag=FlXG9EbDWR3fwyFwGyKTKnw2teTE1Imjra58hsUATT8&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_tending_movies),
            MainApplication.INSTANCE.getString(R.string.album_id_trending_movies),
           "https://sun9-17.userapi.com/impg/Ovl3hIhYuZgQsHbytJuKXnfrxAkNlViwv5IUfw/_x3UHIGBy7M.jpg?size=604x483&quality=95&sign=cb0afa4ba998665ab993d248f2165c6d&c_uniq_tag=YAirSlTXqNWuh4Y0RI2bArQ4gHpe4XMHI6zXCsU_pKM&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Flowers),
            MainApplication.INSTANCE.getString(R.string.album_id_flowers),
            "https://sun9-69.userapi.com/impg/MDVZIekcr3qhl1vU2SCMgdQcVA7U2GYoi7wvmA/z33sv6wEl7s.jpg?size=340x604&quality=95&sign=e33febabf618ca517b9d803f2a0fcc7e&c_uniq_tag=dEIT5QvkK5v2qVMtLVBFj5-cHTuVyF8Zfe_3kqZMUVI&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Motivation),
            MainApplication.INSTANCE.getString(R.string.album_id_motivation),
            "https://sun9-67.userapi.com/impg/_8bhaCOHp3LbyDx9wr71lN2U_74__NExxZM_2A/h7ywbYYGaBA.jpg?size=402x604&quality=95&sign=ad0cbbfa085919650a5af1d506e4bcdd&c_uniq_tag=qp-UleGzv7L_x4_2T1w-pU9iPMYp4kpGNT58Cyanx9o&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Sport),
            MainApplication.INSTANCE.getString(R.string.album_id_sport),
            "https://sun9-27.userapi.com/impg/raMgFMpn6Yg28BpKyV8BWaNP63-GRlElzTiazQ/5yYDZitQHSI.jpg?size=604x453&quality=95&sign=35d561e2a3d70a15b72d2f58ded38de6&c_uniq_tag=XDFGRiKfv5SasFj5vgpFpwE-U9bBarOXJXaZUE5ZOME&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Fields),
            MainApplication.INSTANCE.getString(R.string.album_id_fields),
           "https://sun9-56.userapi.com/impg/Z0HJmoVSlLG7fn3ZMALevqsXBySnXLhAepbc3A/TJYNqBLEpHQ.jpg?size=604x401&quality=95&sign=11bca6fae3cf40e1fbcad571de88e3ec&c_uniq_tag=nMkQxFKH406rMElSBQqIeDWWklYdH1cGRp4Jy-NlQvs&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Autumn),
            MainApplication.INSTANCE.getString(R.string.album_id_autumn),
            "https://sun9-70.userapi.com/impg/ruy6WIUvCdfXDN_uZ_drM5NrFlMiILQDCcoTvw/sLquhpd91gE.jpg?size=402x604&quality=95&sign=be822b4cd753e220c7c9b2fc0209ecdd&c_uniq_tag=xHqDACgbBrwZceFzolbA-aTXfrowyzMBz8vvHG_rPSY&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Night),
            MainApplication.INSTANCE.getString(R.string.album_id_night),
            "https://sun9-30.userapi.com/impg/Ci0KMHBuHkHTTj5yQ_r_qjHkIgW1gZa52odhEA/xD84fdIF5Hg.jpg?size=402x604&quality=95&sign=db411b575d4ffc6eb14c7aa5a800b6a9&c_uniq_tag=LKCNwkgFO8S78sc5e0E693ycz0e5UuNJwCqOg4vUlQg&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Water),
            MainApplication.INSTANCE.getString(R.string.album_id_water),
            "https://sun9-36.userapi.com/impg/dS70FklrO8BNBOOah4WyWnuK3xqBsdZnz4Idsg/Bvm7aTPNnds.jpg?size=402x604&quality=95&sign=b5d23ca0fffcedd978d3ceb80e739e96&c_uniq_tag=NxSyLyG2el-yhuXjHL__HGl_mrlrHIX64-5n7QJZjPo&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Snow),
            MainApplication.INSTANCE.getString(R.string.album_id_snow),
            "https://sun9-30.userapi.com/impg/A6Rnju6b84mR1jYF0e12kA8wQS4gZQHIJQoU0g/0d3cc8Hg3yA.jpg?size=604x377&quality=95&sign=de9c0409418f0612c40226ffae3b42c9&c_uniq_tag=2xrouLV3MiIH7xuFqW6EO4b5syY8wH5FiQfS1caIwXs&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Sky),
            MainApplication.INSTANCE.getString(R.string.album_id_sky),
            "https://sun9-22.userapi.com/impg/3kyEFu7YXxprARSUqCGPpVDogIzCHDpvKdyUsQ/D0jDyG9JAJM.jpg?size=604x403&quality=95&sign=0620564df07aacc6b192672c812bea37&c_uniq_tag=e29otXeG3d8nVNKYBn1DQAJukf30upKNBdTsa2aBa-M&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Cities),
            MainApplication.INSTANCE.getString(R.string.album_id_cities),
            "https://sun9-4.userapi.com/impg/6VWjv_bEfHbp6-ObzNR_zO8nnznA3sT6udefLQ/h_DBGrRB8KU.jpg?size=402x604&quality=95&sign=fe43d01e40186c439bdff70fd82396ae&c_uniq_tag=zrqGHegPBgE8GBuQdnimMGE4UCKxmf-xCEYB_UFAoss&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Sunset),
            MainApplication.INSTANCE.getString(R.string.album_id_sunset),
            "https://sun9-39.userapi.com/impg/xOHnP0ygVI67Af2vR-QJz-okGdGGMiqHlep-FQ/zbcO28yT174.jpg?size=604x377&quality=95&sign=1cedb391cc83042f55b34f8da5bb8f96&c_uniq_tag=Rke5eyEbk-JlHl-a38vaL9oAcPsuECxABWi4pBNr4Lg&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Mountains),
            MainApplication.INSTANCE.getString(R.string.album_id_mountains),
            "https://sun9-54.userapi.com/impg/7CJz-P0u0C19CR1axJjLb8Kx_VekXvTIFmCK9w/4604xLzYNOU.jpg?size=402x604&quality=95&sign=1ca518febde88d399bf44615fe3796b2&c_uniq_tag=dC3RWIxttUzXDeQU4qrSuJ9vXoLzDjtplnhfNwD_VpE&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Nature),
            MainApplication.INSTANCE.getString(R.string.album_id_nature),
           "https://sun9-77.userapi.com/impg/ONzNtaWBvQBmLCBaXU1Zi6hIQ1nVYdSiVEY_9w/PMkTh2a9zZM.jpg?size=604x377&quality=95&sign=36a6cc331887576e21a0fdf48d71127c&c_uniq_tag=NTPnkZpvXmXHKOmfnTFtuaw848mFBcSBRbqTdNzkywE&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Animals),
            MainApplication.INSTANCE.getString(R.string.album_id_animals),
            "https://sun9-11.userapi.com/impg/Jt6uD2fi9r5RnEc3tKdrstmhjCNiEMhXkKv0pA/Ydt9n7YmOW8.jpg?size=604x402&quality=95&sign=48fc703fa91af2a604f98b299cc4b79c&c_uniq_tag=RokX4jv4cbd-0yEBjswd2fqcaz4DMclrGQA56-E4Oag&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Abstractions),
            MainApplication.INSTANCE.getString(R.string.album_id_abstractions),
        "https://sun9-2.userapi.com/impg/c63Kxsk7qoiV7m2J_-MYkqVvlaj213jjPLMYig/CZCu7wie0lA.jpg?size=402x604&quality=95&sign=545682f07cbed62582e8af9f97f8f364&c_uniq_tag=TODelwUxASbf9w6TzfFXCw4X0G0B3SDfEdwd31LFAIo&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Games),
            MainApplication.INSTANCE.getString(R.string.album_id_games),
        "https://sun9-50.userapi.com/impg/EGAVI91fBQRcZDBDJ5Yp6QB6grgMQXuKaFPu6w/10gS1X0nT08.jpg?size=604x377&quality=95&sign=332cc3d7ab666a7f63254cfe397491ac&c_uniq_tag=YHPfZl2OZvkWRVA3rrmAKAeuiTew00w9kI93126xJuU&type=album"))
        categoriesList.add(CategoriesModel(
            MainApplication.INSTANCE.getString(R.string.category_Anime),
            MainApplication.INSTANCE.getString(R.string.album_id_anime),
            "https://sun9-7.userapi.com/impg/aEG7TNjste6mwvLvRthr4VAkvr2NdHSDIyyWKQ/PTl7-QhxIMw.jpg?size=427x604&quality=95&sign=596ca5ab4898497f815935204d088875&c_uniq_tag=SzrTVmShtPEqY-9GbPKjjc9bLQYJ35RY2YQ_quhwFb0&type=album"))

        return sortCategory(categoriesList, sort)
    }
    private fun sortCategory (categoriesList:ArrayList<CategoriesModel>, sort: String):List<CategoriesModel>{
        return if (sort == "alphabetOrder"){
            val sortedList = categoriesList.sortedBy { it.name }
            sortedList
        } else{
            categoriesList
        }
    }
}