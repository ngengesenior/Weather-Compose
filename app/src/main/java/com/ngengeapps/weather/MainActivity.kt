package com.ngengeapps.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.ngengeapps.weather.ui.AppNavigation
import com.ngengeapps.weather.ui.theme.WeatherTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi

const val TAG = "AppTag"
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel:WeatherViewModel by viewModels()

    @ExperimentalPermissionsApi
    @ExperimentalAnimationApi
    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            WeatherTheme {
                val navController = rememberNavController()

                Surface(color = MaterialTheme.colors.background) {

                    AppNavigation(navController = navController,
                        viewModel = viewModel )
                }
            }
        }
    }
}


