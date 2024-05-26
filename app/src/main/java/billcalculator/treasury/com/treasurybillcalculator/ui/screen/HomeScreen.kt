package billcalculator.treasury.com.treasurybillcalculator.ui.screen


import android.app.Activity
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import billcalculator.treasury.com.treasurybillcalculator.MainActivity
import billcalculator.treasury.com.treasurybillcalculator.R
import billcalculator.treasury.com.treasurybillcalculator.ui.ads.AdmobBanner
import billcalculator.treasury.com.treasurybillcalculator.utils.DecimalFormatter
import billcalculator.treasury.com.treasurybillcalculator.utils.DecimalInputVisualTransformation
import billcalculator.treasury.com.treasurybillcalculator.utils.ScreenSize
import billcalculator.treasury.com.treasurybillcalculator.utils.priceFilter
import billcalculator.treasury.com.treasurybillcalculator.utils.rememberScreenSize
import kotlinx.coroutines.launch
import java.text.DecimalFormatSymbols
import java.util.Locale


/*@Preview(
    name = "small font",
    group = "font scales",
    fontScale = 0.5f
)
@Preview(
    name = "large font",
    group = "font scales",
    fontScale = 1.5f
)*/
//@PreviewScreenSizes
@Preview(showBackground = true)
@Composable
fun HomePreview(viewModel: HomeViewModel = HomeViewModel()) {

    CompactScreen(viewModel = viewModel)
}

//@PreviewScreenSizes
//@Preview(showBackground = true)
@Composable
fun HomeScreen(
    activity: Activity = MainActivity(),
    viewModel: HomeViewModel = HomeViewModel()
) {
    val windowSize: ScreenSize = rememberScreenSize()
    when (windowSize) {
        ScreenSize.Large -> {
            // TODO: Implement for Tablet
            MediumScreen(viewModel = viewModel)
        }

        ScreenSize.Wide -> {
            // TODO: Implement for Landscape
            MediumScreen(viewModel = viewModel)
        }

        ScreenSize.Normal -> {
            // TODO: Implement for Normal Portrait
            CompactScreen(viewModel = viewModel)
        }

    }


}


/*Portrait Screen*/
@Composable
fun CompactScreen(viewModel: HomeViewModel) {

    /*Show Dialog when openAlertDialog update on the click of icon*/
    val openAlertDialog = remember { mutableStateOf(false) }
    CustomDialog(viewModel.openAlertDialog)

    /*Hide or show Keyboard*/
    val keyboardController = LocalSoftwareKeyboardController.current
    /*Go to next focused text field with press of next button on keyboard*/
    val focusRequesters = remember { List(3) { FocusRequester() } }
    var currentFocusedTextFieldIndex by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    val decimalFormatter = DecimalFormatter(symbols = DecimalFormatSymbols(Locale.US))

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(.8f)
                .scrollable(scrollState,Orientation.Vertical)
                .padding(start = 36.dp, end = 36.dp)
        ) {

            Icon(
                modifier = Modifier
                    .padding(top = 46.dp, start = 8.dp, bottom = 8.dp, end = 8.dp)
                    .align(Alignment.Start)
                    .clickable {
                        // TODO: remove this
                        //viewModel.isActiveAd = !viewModel.isActiveAd
                        viewModel.openAlertDialog.value = true
                    },
                imageVector = Icons.Default.Info, contentDescription = "Info about",
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp)
            )

            CustomTextField(
                title = stringResource(R.string.amount),
                value = viewModel.amount,
                onValueChange = {
                    viewModel.amount = it
                    viewModel.finalResult()
                },
                visualTransformation =  DecimalInputVisualTransformation(decimalFormatter),
                modifier = Modifier
                    .onFocusChanged {
                        if (it.isFocused) {
                            currentFocusedTextFieldIndex = 0
                            coroutineScope.launch {
                                scrollState.scrollTo(0)
                            }
                        }
                    },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusRequesters[0].requestFocus()
                    }
                )
            )
            CustomTextField(
                title = stringResource(R.string.duration),
                value = viewModel.duration,
                onValueChange = {
                    viewModel.duration = it
                    viewModel.finalResult()
                },
                modifier = Modifier
                    .focusRequester(focusRequesters[0])
                    .onFocusChanged {
                        if (it.isFocused) {
                            currentFocusedTextFieldIndex = 1
                            coroutineScope.launch {
                                scrollState.scrollTo(400)
                            }
                        }
                    },
                visualTransformation =  DecimalInputVisualTransformation(decimalFormatter),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusRequesters[1].requestFocus()
                    }
                )
            )

            CustomTextField(
                title = stringResource(R.string.bankRate),
                value = viewModel.bankRate,
                onValueChange = {
                    viewModel.bankRate = it
                    viewModel.finalResult()
                },
                modifier = Modifier
                    .focusRequester(focusRequesters[1])
                    .onFocusChanged {
                        if (it.isFocused) {
                            currentFocusedTextFieldIndex = 2
                            coroutineScope.launch {
                                scrollState.scrollTo(400)
                            }
                        }
                    },
                visualTransformation =  DecimalInputVisualTransformation(decimalFormatter),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusRequesters[2].requestFocus()
                    }
                )
            )

            CustomTextField(
                title = stringResource(R.string.year),
                value = viewModel.year,
                onValueChange = {
                    viewModel.year = it
                    viewModel.finalResult()
                },
                modifier = Modifier
                    .focusRequester(focusRequesters[2])
                    .onFocusChanged {
                        if (it.isFocused) {
                            currentFocusedTextFieldIndex = 1
                            coroutineScope.launch {
                                scrollState.scrollTo(400)
                            }
                        }
                    },
                visualTransformation =  DecimalInputVisualTransformation(decimalFormatter),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                )
            )
        }


        Column(
            modifier = Modifier
                .weight(.36f)
                .align(Alignment.CenterHorizontally)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(horizontal = 26.dp, vertical = 16.dp)
                .navigationBarsPadding()
        ) {

            CustomTextField(
                title = stringResource(R.string.interest),
                value = viewModel.interest,
                onValueChange = {}
            )

            /*Banner Ad*/
            AdmobBanner(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f)
                    .fillMaxWidth(),
                isActiveAd = viewModel.isActiveAd
            )
        }
    }

}

/*Portrait Screen*/
@Composable
fun CompactScreenREal(viewModel: HomeViewModel) {

    /*Show Dialog when openAlertDialog update on the click of icon*/
    val openAlertDialog = remember { mutableStateOf(false) }
    CustomDialog(viewModel.openAlertDialog)

    /*Hide or show Keyboard*/
    val keyboardController = LocalSoftwareKeyboardController.current
    /*Go to next focused text field with press of next button on keyboard*/
    val focusRequesters = remember { List(3) { FocusRequester() } }
    var currentFocusedTextFieldIndex by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(.8f)
                .padding(start = 36.dp, end = 36.dp)
        ) {

            Icon(
                modifier = Modifier
                    .padding(top = 46.dp, start = 8.dp, bottom = 8.dp, end = 8.dp)
                    .align(Alignment.Start)
                    .clickable {
                        // TODO: remove this
                        //viewModel.isActiveAd = !viewModel.isActiveAd
                        viewModel.openAlertDialog.value = true
                    },
                imageVector = Icons.Default.Info, contentDescription = "Info about",
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Text(stringResource(R.string.amount))
            SimpleTextField(
                value = viewModel.amount,
                onValueChange = {
                    viewModel.amount = it
                    viewModel.finalResult()
                },
                modifier = Modifier
                    .defaultMinSize(minHeight = 40.dp)
                    .padding(vertical = 8.dp)
                    .height(56.dp)
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .onFocusChanged {
                        if (it.isFocused) {
                            currentFocusedTextFieldIndex = 0
                            coroutineScope.launch {
                                scrollState.scrollTo(0)
                            }
                        }
                    }
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusRequesters[0].requestFocus()
                    }
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    focusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    cursorColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    disabledLabelColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            Text(stringResource(R.string.duration))
            SimpleTextField(
                value = viewModel.duration,
                onValueChange = {
                    viewModel.duration = it
                    viewModel.finalResult()
                },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 16.dp)
                    .focusRequester(focusRequesters[0])
                    .onFocusChanged {
                        if (it.isFocused) {
                            currentFocusedTextFieldIndex = 1
                            coroutineScope.launch {
                                scrollState.scrollTo(400)
                            }

//                        coroutineScope.launch { scrollState.animateScrollTo(scrollState.value - 1000) }
                        }
                    }
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusRequesters[1].requestFocus()
                    }
                ),
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

            Text(stringResource(R.string.bankRate))
            SimpleTextField(
                value = viewModel.bankRate,
                onValueChange = {
                    viewModel.bankRate = it
                    viewModel.finalResult()
                },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 16.dp)
                    .focusRequester(focusRequesters[1])
                    .onFocusChanged {
                        if (it.isFocused) {
                            currentFocusedTextFieldIndex = 2
                            coroutineScope.launch {
                                scrollState.scrollTo(800)
                            }
                        }
                    }
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusRequesters[2].requestFocus()
                    }
                ),
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

            Text(stringResource(R.string.year))
            SimpleTextField(
                value = viewModel.year,
                onValueChange = {
                    viewModel.year = it
                    viewModel.finalResult()
                },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 16.dp)
                    .focusRequester(focusRequesters[2])
                    .onFocusChanged {
                        if (it.isFocused) {
                            currentFocusedTextFieldIndex = 3
                            coroutineScope.launch {
                                scrollState.scrollTo(1200)
                            }
                        }
                    }
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
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
        }


        Column(
            modifier = Modifier
                .weight(.32f)
                .align(Alignment.CenterHorizontally)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(horizontal = 26.dp, vertical = 16.dp)
                .navigationBarsPadding()
        ) {
            Text(stringResource(R.string.interest))
            SimpleTextField(
                value = viewModel.interest,
                onValueChange = {},
                modifier = Modifier
                    .defaultMinSize(minHeight = 40.dp)
                    .padding(vertical = 8.dp)
                    .height(56.dp)
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(bottom = 2.dp)
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


            /*Banner Ad*/
            AdmobBanner(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f)
                    .fillMaxWidth(),
                isActiveAd = viewModel.isActiveAd
            )
        }
    }

}

/*Portrait Screen*/
@Composable
fun CompactScreenNormal(activity: Activity, viewModel: HomeViewModel) {

    /*Show Dialog when openAlertDialog update on the click of icon*/
    val openAlertDialog = remember { mutableStateOf(false) }
    CustomDialog(viewModel.openAlertDialog)

    /*Hide or show Keyboard*/
    val keyboardController = LocalSoftwareKeyboardController.current
    /*Go to next focused text field with press of next button on keyboard*/
    val focusRequesters = remember { List(3) { FocusRequester() } }
    var currentFocusedTextFieldIndex by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()


    Column(modifier = Modifier.fillMaxSize()) {


        Column(
            modifier = Modifier.padding(start = 36.dp, end = 36.dp)
        ) {

            Icon(
                modifier = Modifier
                    .padding(top = 46.dp, start = 8.dp, bottom = 8.dp, end = 8.dp)
                    .align(Alignment.Start)
                    .clickable {
                        // TODO: remove this
                        //viewModel.isActiveAd = !viewModel.isActiveAd
                        viewModel.openAlertDialog.value = true
                    },
                imageVector = Icons.Default.Info, contentDescription = "Info about",
            )

            VerticalDivider(
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Text(stringResource(R.string.amount))


            TextField(
                value = viewModel.amount,
                onValueChange = {
                    viewModel.amount = it
                    viewModel.finalResult()
                },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .onFocusChanged {
                        if (it.isFocused) {
                            currentFocusedTextFieldIndex = 0
                            coroutineScope.launch {
                                scrollState.scrollTo(0)
                            }
                        }
                    }
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusRequesters[0].requestFocus()
                    }
                ),
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

            Text(stringResource(R.string.duration))
            TextField(
                value = viewModel.duration,
                onValueChange = {
                    viewModel.duration = it
                    viewModel.finalResult()
                },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .focusRequester(focusRequesters[0])
                    .onFocusChanged {
                        if (it.isFocused) {
                            currentFocusedTextFieldIndex = 1
                            coroutineScope.launch {
                                scrollState.scrollTo(400)
                            }

//                        coroutineScope.launch { scrollState.animateScrollTo(scrollState.value - 1000) }
                        }
                    }
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusRequesters[1].requestFocus()
                    }
                ),
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

            Text(stringResource(R.string.bankRate))
            TextField(
                value = viewModel.bankRate,
                onValueChange = {
                    viewModel.bankRate = it
                    viewModel.finalResult()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .focusRequester(focusRequesters[1])
                    .onFocusChanged {
                        if (it.isFocused) {
                            currentFocusedTextFieldIndex = 2
                            coroutineScope.launch {
                                scrollState.scrollTo(800)
                            }
                        }
                    }
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusRequesters[2].requestFocus()
                    }
                ),
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

            Text(stringResource(R.string.year))
            TextField(
                value = viewModel.year,
                onValueChange = {
                    viewModel.year = it
                    viewModel.finalResult()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 186.dp)
                    .focusRequester(focusRequesters[2])
                    .onFocusChanged {
                        if (it.isFocused) {
                            currentFocusedTextFieldIndex = 3
                            coroutineScope.launch {
                                scrollState.scrollTo(1200)
                            }
                        }
                    }
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
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

        }


        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(horizontal = 26.dp, vertical = 16.dp)
        ) {
            Text(stringResource(R.string.interest))
            TextField(
                value = viewModel.interest,
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


            /*Banner Ad*/
            AdmobBanner(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                isActiveAd = viewModel.isActiveAd
            )
        }
    }
}

/*Portrait Screen*/
@Composable
fun CompactScreenLazy(activity: Activity, viewModel: HomeViewModel) {

    /*Show Dialog when openAlertDialog update on the click of icon*/
    val openAlertDialog = remember { mutableStateOf(false) }
    CustomDialog(viewModel.openAlertDialog)

    /*Hide or show Keyboard*/
    val keyboardController = LocalSoftwareKeyboardController.current
    /*Go to next focused text field with press of next button on keyboard*/
    val focusRequesters = remember { List(3) { FocusRequester() } }
    var currentFocusedTextFieldIndex by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Add a single item
        item {
            Column(
                modifier = Modifier
                    .padding(start = 36.dp, end = 36.dp)
                    .statusBarsPadding()
            ) {

                Icon(
                    modifier = Modifier
                        .padding(top = 46.dp, start = 8.dp, bottom = 8.dp, end = 8.dp)
                        .align(Alignment.Start)
                        .clickable {
                            // TODO: remove this
                            //viewModel.isActiveAd = !viewModel.isActiveAd
                            viewModel.openAlertDialog.value = true
                        },
                    imageVector = Icons.Default.Info, contentDescription = "Info about",
                )

                VerticalDivider(
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                Text(stringResource(R.string.amount))


                TextField(
                    value = viewModel.amount,
                    onValueChange = {
                        viewModel.amount = it
                        viewModel.finalResult()
                    },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                        .onFocusChanged {
                            if (it.isFocused) {
                                currentFocusedTextFieldIndex = 0
                                coroutineScope.launch {
                                    scrollState.scrollTo(0)
                                }
                            }
                        },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusRequesters[0].requestFocus()
                        }
                    ),
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

                Text(stringResource(R.string.duration))
                TextField(
                    value = viewModel.duration,
                    onValueChange = {
                        viewModel.duration = it
                        viewModel.finalResult()
                    },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .focusRequester(focusRequesters[0])
                        .onFocusChanged {
                            if (it.isFocused) {
                                currentFocusedTextFieldIndex = 1
                                coroutineScope.launch {
                                    scrollState.scrollTo(400)
                                }

//                        coroutineScope.launch { scrollState.animateScrollTo(scrollState.value - 1000) }
                            }
                        },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusRequesters[1].requestFocus()
                        }
                    ),
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

                Text(stringResource(R.string.bankRate))
                TextField(
                    value = viewModel.bankRate,
                    onValueChange = {
                        viewModel.bankRate = it
                        viewModel.finalResult()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .focusRequester(focusRequesters[1])
                        .onFocusChanged {
                            if (it.isFocused) {
                                currentFocusedTextFieldIndex = 2
                                coroutineScope.launch {
                                    scrollState.scrollTo(800)
                                }
                            }
                        },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusRequesters[2].requestFocus()
                        }
                    ),
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

                Text(stringResource(R.string.year))
                TextField(
                    value = viewModel.year,
                    onValueChange = {
                        viewModel.year = it
                        viewModel.finalResult()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 186.dp)
                        .focusRequester(focusRequesters[2])
                        .onFocusChanged {
                            if (it.isFocused) {
                                currentFocusedTextFieldIndex = 3
                                coroutineScope.launch {
                                    scrollState.scrollTo(1200)
                                }
                            }
                        },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
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

            }
        }


        item {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(horizontal = 26.dp, vertical = 16.dp)
                    .navigationBarsPadding()
            ) {
                Text(stringResource(R.string.interest))
                TextField(
                    value = viewModel.interest,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusable(false)
                        .clickable(false) {
                            Log.d("TAG", "CryptoInterestCalculatorKeyboardAware: Clickable false")
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


                /*Banner Ad*/
                AdmobBanner(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    isActiveAd = viewModel.isActiveAd
                )
            }
        }
    }

}


/*Landscape or Tablet view for now*/
@Composable
fun MediumScreen(viewModel: HomeViewModel) {


    /*Show Dialog when openAlertDialog update on the click of icon*/
    val openAlertDialog = remember { mutableStateOf(false) }
    CustomDialog(viewModel.openAlertDialog)

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequesters = remember { List(3) { FocusRequester() } }
    var currentFocusedTextFieldIndex by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {

            Column(
                modifier = Modifier
                    .padding(start = 36.dp, end = 36.dp)
            ) {

                Icon(
                    modifier = Modifier
                        .padding(top = 30.dp, bottom = 8.dp, end = 8.dp)
                        .align(Alignment.End)
                        .clickable {
                            // TODO: remove this
                            //viewModel.isActiveAd = !viewModel.isActiveAd
                            viewModel.openAlertDialog.value = true
                        },
                    imageVector = Icons.Default.Info, contentDescription = "Info about",
                )
                VerticalDivider()


                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    ) {


                        Text(stringResource(R.string.amount))
                        TextField(
                            value = viewModel.amount,
                            onValueChange = {
                                viewModel.amount = it
                                viewModel.finalResult()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                                .onFocusChanged {
                                    if (it.isFocused) {
                                        currentFocusedTextFieldIndex = 0
                                        coroutineScope.launch {
                                            scrollState.scrollTo(0)
                                        }
                                    }
                                },

                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusRequesters[0].requestFocus()
                                }
                            ),
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
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        Text(stringResource(R.string.duration))
                        TextField(
                            value = viewModel.duration,
                            onValueChange = {
                                viewModel.duration = it
                                viewModel.finalResult()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                                .focusRequester(focusRequesters[0])
                                .onFocusChanged {
                                    if (it.isFocused) {
                                        currentFocusedTextFieldIndex = 1
                                        coroutineScope.launch {
                                            scrollState.scrollTo(0)
                                        }

//                        coroutineScope.launch { scrollState.animateScrollTo(scrollState.value - 1000) }
                                    }
                                },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusRequesters[1].requestFocus()
                                }
                            ),
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
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    ) {

                        Text(stringResource(R.string.bankRate))
                        TextField(
                            value = viewModel.bankRate,
                            onValueChange = {
                                viewModel.bankRate = it
                                viewModel.finalResult()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                                .focusRequester(focusRequesters[1])
                                .onFocusChanged {
                                    if (it.isFocused) {
                                        currentFocusedTextFieldIndex = 2
                                        coroutineScope.launch {
                                            scrollState.scrollTo(400)
                                        }
                                    }
                                },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusRequesters[2].requestFocus()
                                }
                            ),
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
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        Text(stringResource(R.string.year))

                        TextField(
                            value = viewModel.year,
                            onValueChange = {
                                viewModel.year = it
                                viewModel.finalResult()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                                .focusRequester(focusRequesters[2])
                                .onFocusChanged {
                                    if (it.isFocused) {
                                        currentFocusedTextFieldIndex = 3
                                        coroutineScope.launch {
                                            scrollState.scrollTo(400)
                                        }
                                    }
                                },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusRequesters[2].freeFocus()
                                    keyboardController?.hide()
                                }
                            ),
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
                    }
                }


                /*Banner Ad*/
                AdmobBanner(
                    modifier = Modifier
                        .padding(bottom = 200.dp, start = 10.dp, end = 10.dp, top = 10.dp)
                        .fillMaxWidth(),
                    landscape = true
                )


            }
        }
        item {


            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(horizontal = 26.dp, vertical = 36.dp)
            ) {
                Text(stringResource(R.string.interest))
                TextField(
                    value = viewModel.interest,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
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
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    placeholder: @Composable (() -> Unit)? = null,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    cursorBrush: Brush = SolidColor(Color.White),
    colors: TextFieldColors = TextFieldDefaults.colors(),
    shape: Shape = RoundedCornerShape(8.dp),
) {
    BasicTextField(modifier = modifier
        .background(MaterialTheme.colorScheme.onSurface, shape = shape),
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        maxLines = maxLines,
        enabled = enabled,
        readOnly = readOnly,
        interactionSource = interactionSource,
        textStyle = textStyle.copy(color = MaterialTheme.colorScheme.onSecondaryContainer),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        onTextLayout = onTextLayout,
        cursorBrush = cursorBrush,
        decorationBox = { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = {
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        innerTextField()
                    }
                },
                enabled = enabled,
                colors = colors,
                singleLine = singleLine,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                    top = 0.dp,
                    bottom = 0.dp
                ),
                placeholder = {
                    if (value.isEmpty() && placeholder != null) {
                        Box(
                            modifier = Modifier.fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            placeholder()
                        }
                    }
                },
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon
            )
        }
    )
}


@Composable
fun CustomTextField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val decimalFormatter = DecimalFormatter(symbols = DecimalFormatSymbols(Locale.US))
    Text(title)

    SimpleTextField(
        value = value,
        onValueChange = {
            var text = ""
            text = if (it.startsWith("0")) {
                ""
            } else {
                it
            }
            onValueChange(DecimalFormatter().cleanup(text))
        },
        visualTransformation = visualTransformation,
        modifier = modifier
            .height(68.dp)
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            focusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
            unfocusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
            cursorColor = MaterialTheme.colorScheme.onSecondaryContainer,
            disabledLabelColor = MaterialTheme.colorScheme.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}










