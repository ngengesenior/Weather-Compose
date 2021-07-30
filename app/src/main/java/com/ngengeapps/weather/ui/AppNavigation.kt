package com.ngengeapps.weather.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.ngengeapps.weather.WeatherViewModel

sealed class Screen(val route:String) {
    object Home:Screen("home")
    object DayDetails:Screen("details")

}


@ExperimentalAnimationApi
@ExperimentalPermissionsApi
@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel:WeatherViewModel

) {
    NavHost(startDestination = Screen.Home.route,
        navController = navController){

        composable(Screen.Home.route){
            WeatherUI(viewModel = viewModel, navController)
        }

        composable(Screen.DayDetails.route){
            DayDetailUI(navController = navController,viewModel = viewModel)
        }
    }
}


