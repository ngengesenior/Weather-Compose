package com.ngengeapps.weather.ui

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.*
import com.ngengeapps.weather.openSettings

@ExperimentalPermissionsApi
@Composable
fun LocationPermissionUI(
    permissionAcceptedContent: @Composable (() -> Unit)
) {
    val context = LocalContext.current
    var doNotShowRationale by rememberSaveable {
        mutableStateOf(false)
    }
    val locationPermissionState = rememberMultiplePermissionsState(permissions = listOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION))
    PermissionsRequired(
        multiplePermissionsState = locationPermissionState,
        permissionsNotGrantedContent = {
            if (doNotShowRationale) {
                Text(text = "We need Location Permission in order to show you current weather conditions")
            } else {

                Card(elevation = 2.dp,shape = RoundedCornerShape(8.dp)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(8.dp)) {

                        Text(
                            text = "Location Permissions",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "We need Location Permission in order to show you current weather conditions",
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = { locationPermissionState.launchMultiplePermissionRequest() },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = "OK")
                            }

                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = {
                                    doNotShowRationale = true
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                            ) {
                                Text(text = "NO")
                            }
                        }
                    }
                }
            }
        },
        permissionsNotAvailableContent = {
            Card(elevation = 2.dp,shape = RoundedCornerShape(8.dp)) {


                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Location Permissions",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "We need Location Permission in order to show you current weather conditions.Please " +
                                "grant us access on the settings screen"
                    )
                    Button(onClick = { context.openSettings() }) {
                        Text(text = "Open Settings")
                    }
                }
            }
        }
    ) {
        permissionAcceptedContent()
    }
}
