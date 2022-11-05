package com.wallpapers.hd.dry

import android.app.Activity
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory

object RateApp {
    private var manager: ReviewManager? = null
    private fun rateApp(activity:Activity){
        manager = ReviewManagerFactory.create(activity)
        val request = manager?.requestReviewFlow()
        request?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                val flow = manager?.launchReviewFlow(activity, reviewInfo)
                flow?.addOnCompleteListener { _ ->

                }
            }
        }
    }
}