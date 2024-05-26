package billcalculator.treasury.com.treasurybillcalculator.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import billcalculator.treasury.com.treasurybillcalculator.R

@Composable
fun DialogWithIcon(
    onDismissRequest: () -> Unit,
    icon: ImageVector,
    imageDescription: String,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Icon(icon, contentDescription = imageDescription)
                Text(
                    text = stringResource(id = R.string.app_name),
                    modifier = Modifier.padding(16.dp),
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outline, thickness = 2.dp)
                Text(
                    text = stringResource(id = R.string.description),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.secondary, thickness = 1.dp)
                Text(
                    text = stringResource(id = R.string.how_to_use),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.secondary, thickness = 1.dp)
                Text(
                    text = stringResource(id = R.string.disclaimer),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.secondary, thickness = 1.dp)


                TextButton(
                    onClick = { onDismissRequest() },
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text("Dismiss")
                }

            }
        }
    }
}


@Composable
fun CustomDialog(
    openDialogCustom: MutableState<Boolean>
) {

    when {
        openDialogCustom.value -> {
            Dialog(onDismissRequest = { openDialogCustom.value = false }) {
                CustomDialogUI(openDialogCustom = openDialogCustom)
            }
        }
    }
}

enum class CustomDialogType {
    Default,
    Description,
    HowToUse,
    Disclaimer
}

//Layout
@Composable
fun CustomDialogUI(
    modifier: Modifier = Modifier, openDialogCustom: MutableState<Boolean>
) {
    val scrollState = rememberScrollState()

    val state = remember {
        mutableStateOf(CustomDialogType.Default)
    }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.inverseSurface,
        ),
        //shape = MaterialTheme.shapes.medium,
        shape = RoundedCornerShape(12.dp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier
            .padding(10.dp, 5.dp, 10.dp, 10.dp)
            .background(MaterialTheme.colorScheme.inverseSurface)

    ) {
        Column(modifier.verticalScroll(scrollState)) {

            //.......................................................................

            /*
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null, // decorative
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.outline
                ),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(70.dp)
                    .fillMaxWidth(),

                )
*/
            Column(
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 0.dp
                )
            ) {


                when (state.value) {
                    CustomDialogType.Default -> {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outline,
                            thickness = 2.dp
                        )
                        Text(
                            text = stringResource(id = R.string.description),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = {
                                        state.value = CustomDialogType.Description
                                    },
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple()
                                )
                                .padding(vertical = 12.dp, horizontal = 25.dp),
                        )
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outline,
                            thickness = 0.5.dp
                        )
                        Text(
                            text = stringResource(id = R.string.how_to_use),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = {
                                        state.value = CustomDialogType.HowToUse
                                    },
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple()
                                )
                                .padding(vertical = 12.dp, horizontal = 25.dp),
                        )
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outline,
                            thickness = 0.5.dp
                        )
                        Text(
                            text = stringResource(id = R.string.disclaimer),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    state.value = CustomDialogType.Disclaimer
                                }
                                .padding(vertical = 12.dp, horizontal = 25.dp),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.inverseOnSurface
                        )
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outline,
                            thickness = 0.5.dp
                        )
                    }

                    CustomDialogType.Description -> {
                        AlertDialogExample(
                            onDismissRequest = { state.value = CustomDialogType.Default },
                            dialogTitle = stringResource(id = R.string.description),
                            dialogText = stringResource(id = R.string.description_details)
                        )
                    }

                    CustomDialogType.HowToUse -> {


                        AlertDialogExample(
                            onDismissRequest = { state.value = CustomDialogType.Default },
                            dialogTitle = stringResource(id = R.string.how_to_use),
                            dialogText = stringResource(id = R.string.how_to_use_details)
                        )
                    }

                    CustomDialogType.Disclaimer -> {
                        AlertDialogExample(
                            onDismissRequest = { state.value = CustomDialogType.Default },
                            dialogTitle = stringResource(id = R.string.disclaimer),
                            dialogText = stringResource(id = R.string.disclaimer_details)
                        )
                    }
                }


            }


            //.......................................................................

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(all = 10.dp),
                horizontalArrangement = Arrangement.End
            ) {
                if (state.value != CustomDialogType.Default) {
                    TextButton(onClick = {
                        state.value = CustomDialogType.Default
                    }) {
                        Text(
                            "Back",
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                TextButton(onClick = {
                    openDialogCustom.value = false
                }) {
                    Text(
                        "CLOSE",
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onError,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, end = 8.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }


        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {

    DialogWithText(
        onDismissRequest, dialogTitle, dialogText
    )

    /*AlertDialog(

        modifier = Modifier.fillMaxSize(),
        properties = DialogProperties(usePlatformDefaultWidth = false),
        title = {
            Text(
                modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface),
                text = dialogTitle
            )
        },
        text = {
            Text(
                modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface),
                text = dialogText
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {},
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                },
                modifier = Modifier.padding(end = 12.dp)
            ) {
                Text("CLOSE", color = MaterialTheme.colorScheme.onError)
            }
        }
    )*/
}

@Composable
fun DialogWithText(
    onDismissRequest: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {

        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.inverseSurface,
            ),
            //shape = MaterialTheme.shapes.medium,
            shape = RoundedCornerShape(12.dp),
            // modifier = modifier.size(280.dp, 240.dp)
            modifier = Modifier
                .padding(10.dp, 5.dp, 10.dp, 10.dp)
                .background(MaterialTheme.colorScheme.inverseSurface)

        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = dialogTitle,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    modifier = Modifier.padding(16.dp).align(Alignment.Start),
                )
                Text(
                    text = dialogText,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    modifier = Modifier.padding(16.dp).align(Alignment.Start),
                )

                TextButton(
                    onClick = {
                        onDismissRequest()
                    },
                    modifier = Modifier.padding(end = 12.dp).align(Alignment.End),
                ) {
                    Text("CLOSE", color = MaterialTheme.colorScheme.onError)
                }
            }
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@Preview(name = "Custom Dialog")
@Composable
fun MyDialogUIPreview() {
    CustomDialogUI(openDialogCustom = mutableStateOf(false))
}