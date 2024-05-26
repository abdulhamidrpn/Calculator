package billcalculator.treasury.com.treasurybillcalculator.ui.screen

import android.icu.text.NumberFormat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.util.Locale

class HomeViewModel : ViewModel() {

    var isActiveAd by mutableStateOf(true)

    var showInterstitialAd by mutableStateOf(0)
    var openAlertDialog =  mutableStateOf(false)

//    val numberFormat = NumberFormat.getNumberInstance(Locale.US) // Specify US locale for comma format
//    val formattedAmount = numberFormat.format(amount.toDouble()) // Convert to double for formatting
//    val numberFormat = String.format("%,.2f")// Convert to double for formatting

    var amount by mutableStateOf("")
    var duration by mutableStateOf("")
    var bankRate by mutableStateOf("")
    var year by mutableStateOf("")
    var interest by mutableStateOf("")

    fun finalResult() {
        if (amount.isEmpty() || duration.isEmpty() || bankRate.isEmpty() || year.isEmpty()) {
            interest = "Fill all the input!"
            return
        }

        try {

            val p = amount.replace(",","").replace(" ","").replace("-","").trim().toFloat()
            val t = duration.replace(",","").replace(" ","").replace("-","").trim().toFloat()
            val r = bankRate.replace(",","").replace(" ","").replace("-","").trim().toFloat()
            val y = year.replace(",","").replace(" ","").replace("-","").trim().toFloat()
            val finalValue = p * r * t / y * 100 / 10000
            interest = String.format("%,.2f", finalValue)//finalValue.toString()//
        }catch(e: NumberFormatException){
            interest = "Please input valid numbers."
        }
    }



    init {

        viewModelScope.launch {
            while (isActive) {
                delay(60000L) // 40 seconds in milliseconds
                showInterstitialAd += 1 // Update the state
            }
        }
    }

}