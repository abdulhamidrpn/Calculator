package billcalculator.treasury.com.treasurybillcalculator

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import billcalculator.treasury.com.treasurybillcalculator.ui.screen.HomeScreen
import billcalculator.treasury.com.treasurybillcalculator.ui.screen.HomeViewModel
import billcalculator.treasury.com.treasurybillcalculator.ui.theme.JetTheme
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


/*
*
*
@Preview(
    name = "small font",
    group = "font scales",
    fontScale = 0.5f
)
@Preview(
    name = "large font",
    group = "font scales",
    fontScale = 1.5f
)
//@PreviewScreenSizes
*
*
* */


class MainActivity : ComponentActivity() {
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        /*enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                ContextCompat.getColor(this, R.color.immersive_sys_ui)
            )
        )*/

        // Handle the splash screen transition.
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)


// Get the ViewModel instance
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        setContent {
            App(this, viewModel)
//            MyScreen()
        }

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
//            val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
//            view.updatePadding(bottom = bottom)
//            insets
//        }


        /*Important for Scrolling also remember to update resize in manifest.*/
        WindowCompat.setDecorFitsSystemWindows(window, false)

        initializeAd()
    }


    val ad_delay = 40000 // 1 minute in milliseconds
    var mInterstitialAd: InterstitialAd? = null
    private val TAG = "Main"
    private fun initializeAd() {
        MobileAds.initialize(this) { }

        loadInterstitialAd()
    }

    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            getString(R.string.admob_interstitial),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    mInterstitialAd = interstitialAd
                    // Schedule the next ad after 1 minute.

                    Log.d(TAG, "showAd: ${viewModel.isActiveAd}")
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (viewModel.isActiveAd) showAd()
                    }, ad_delay.toLong())
                    mInterstitialAd!!.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdClicked() {
                                // Called when a click is recorded for an ad.
                                Log.d(TAG, "Ad was clicked.")
                            }

                            override fun onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d(TAG, "Ad dismissed fullscreen content.")
                                mInterstitialAd = null
                                loadInterstitialAd()
                            }

                            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                // Called when ad fails to show.
                                Log.e(TAG, "Ad failed to show fullscreen content.")
                                mInterstitialAd = null
                            }

                            override fun onAdImpression() {
                                // Called when an impression is recorded for an ad.
                                Log.d(TAG, "Ad recorded an impression.")
                            }

                            override fun onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d(TAG, "Ad showed fullscreen content.")
                            }
                        }
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    mInterstitialAd = null
                }
            })
    }

    private fun showAd() {
        if (viewModel.isActiveAd && viewModel.showInterstitialAd % 2 != 0) {
            if (mInterstitialAd != null) {
                mInterstitialAd!!.show(this@MainActivity)
            } else {
                loadInterstitialAd()
            }
        }
    }
}


@Preview(showBackground = true)
@Preview(showSystemUi = true)
@Composable
fun AppPreview(
    activity: Activity = MainActivity(), viewModel: HomeViewModel = HomeViewModel()
) {
    App(activity, viewModel)
}


@Composable
fun App(
    activity: Activity, viewModel: HomeViewModel
) {
    JetTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeScreen(activity, viewModel)
        }
    }
}





