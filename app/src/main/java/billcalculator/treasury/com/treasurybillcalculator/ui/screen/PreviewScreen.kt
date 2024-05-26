package billcalculator.treasury.com.treasurybillcalculator.ui.screen


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import billcalculator.treasury.com.treasurybillcalculator.R
import billcalculator.treasury.com.treasurybillcalculator.ui.ads.AdmobBanner


@Preview(showBackground = true)
@Composable
fun PreviewScreen() {

    TrialScreen()
}


/*Portrait Screen*/
@Composable
fun TrialScreen() {
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {

        // Column composable
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Icon(
                modifier = Modifier
                    .padding(all = 16.dp)
                    .align(Alignment.Start),
                imageVector = Icons.Default.Info, contentDescription = "Info about",
            )
            HorizontalDivider()
            // Child components of the Column
            Text(text = "Item 1", modifier = Modifier.background(Color.Gray))
            Text(text = "Item 2", modifier = Modifier.background(Color.LightGray))
            Text(text = "Item 3", modifier = Modifier.background(Color.Gray))
        }

        /*Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Icon(
                modifier = Modifier
                    .padding(top = 46.dp, start = 8.dp, bottom = 8.dp, end = 8.dp)
                    .align(Alignment.Start),
                imageVector = Icons.Default.Info, contentDescription = "Info about",
            )
*//*
            VerticalDivider(
                modifier = Modifier.padding(vertical = 16.dp)
            )*//*
            Text(stringResource(R.string.amount))


            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    focusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    disabledLabelColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = 26.dp, vertical = 16.dp)
            ) {
                Text(stringResource(R.string.interest))
                TextField(
                    value = "Interest",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp))
                        .focusable(false)
                        .clickable(false) {
                            Log.d(
                                "TAG",
                                "CryptoInterestCalculatorKeyboardAware: Clickable false"
                            )
                        },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        focusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        disabledLabelColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )


                *//*Banner Ad*//*
                AdmobBanner(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    isActiveAd = true
                )
            }
        }
*/
    }

}
