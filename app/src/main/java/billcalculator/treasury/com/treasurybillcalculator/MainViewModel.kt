package billcalculator.treasury.com.treasurybillcalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var amount by mutableStateOf("")
    var duration by mutableStateOf("")
    var bankrate by mutableStateOf("")
    var year by mutableStateOf("")
    var strResult by mutableStateOf("")

    fun finalResult() {
        if (amount.isEmpty() || duration.isEmpty() || bankrate.isEmpty() || year.isEmpty()) {
            strResult = "Fill all the input!"
            return
        }
        val p = amount.replace(",","").toFloat()
        val t = duration.replace(",","").toFloat()
        val r = bankrate.replace(",","").toFloat()
        val y = year.replace(",","").toInt()
        strResult = try {
            val finalValue = p * r * t / y * 100 / 10000
            String.format("%,.2f", finalValue)
        }catch(e: NumberFormatException){
            "Please input valid numbers."
        }
    }


}