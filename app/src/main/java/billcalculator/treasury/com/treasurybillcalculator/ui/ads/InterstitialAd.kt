package billcalculator.treasury.com.treasurybillcalculator.ui.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import billcalculator.treasury.com.treasurybillcalculator.R
import billcalculator.treasury.com.treasurybillcalculator.extensions.findActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

fun interstitialAdsContainer(activity: Activity, isActiveAd: Boolean = true) {
    if (!isActiveAd) {
        return
    }

    val TAG = "MainActivity"
    val adRequest = AdRequest.Builder().build()

    InterstitialAd.load(activity, activity.getString(R.string.admob_interstitial),
        adRequest,
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                adError.toString().let { Log.d(TAG, it) }
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                activity.findActivity()?.let {
                    interstitialAd.show(it)
                } ?: {
                    Toast.makeText(activity, "No Activity found.", Toast.LENGTH_SHORT).show()
                }
            }
        })

}