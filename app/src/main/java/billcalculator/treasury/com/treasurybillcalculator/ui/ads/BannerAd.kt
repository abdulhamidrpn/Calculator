package billcalculator.treasury.com.treasurybillcalculator.ui.ads

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import billcalculator.treasury.com.treasurybillcalculator.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView


@Composable
fun AdmobBanner(modifier: Modifier = Modifier,landscape : Boolean = false,isActiveAd: Boolean = true) {
    if (!isActiveAd) {
        /*If Ad is not activated then it shouldn't show any ad.*/
        return
    }
    AndroidView(
        // on below line specifying width for ads.
        modifier = modifier,
        factory = { context ->
            AdView(context).apply {

                val adSize = if (landscape) AdSize.LEADERBOARD
                else AdSize.BANNER

                setAdSize(adSize)

                adUnitId = context.getString(R.string.admob_banner_ad)
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}