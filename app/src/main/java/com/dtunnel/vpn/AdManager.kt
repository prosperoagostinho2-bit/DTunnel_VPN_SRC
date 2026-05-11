package com.dtunnel.vpn

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

object AdManager {
    private var rewardedAd: RewardedAd? = null
    private const val REWARDED_ID = "ca-app-pub-1528818821765226/4171899089"

    fun initialize(context: Context) {
        MobileAds.initialize(context)
        loadRewardedAd(context)
    }

    fun loadRewardedAd(context: Context) {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(context, REWARDED_ID, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdLoaded(ad: RewardedAd) {
                rewardedAd = ad
            }
            override fun onAdFailedToLoad(error: LoadAdError) {
                rewardedAd = null
            }
        })
    }

    fun showRewardedAd(activity: Activity, onRewarded: () -> Unit, onFailed: () -> Unit) {
        val ad = rewardedAd
        if (ad != null) {
            ad.show(activity) { onRewarded() }
            rewardedAd = null
            loadRewardedAd(activity)
        } else {
            onFailed()
        }
    }
}
