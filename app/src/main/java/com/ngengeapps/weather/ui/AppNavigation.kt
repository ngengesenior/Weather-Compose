package com.ngengeapps.weather.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.ngengeapps.weather.WeatherViewModel
import com.ngengeapps.weather.slideInAnimation
import com.ngengeapps.weather.slideOutAnim

sealed class Screen(val route:String) {
    object Home:Screen("home")
    object DayDetails:Screen("details")

}


/*@ExperimentalAnimationApi
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
}*/

@ExperimentalAnimationApi
@ExperimentalPermissionsApi
@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel:WeatherViewModel

) {
    AnimatedNavHost(startDestination = Screen.Home.route,
        navController = navController){

        composable(Screen.Home.route,enterTransition = {_,_ -> slideInAnimation},
        exitTransition = {_,_ -> slideOutAnim}){
            WeatherUI(viewModel = viewModel, navController)
        }



        composable(Screen.DayDetails.route,enterTransition = {_,_ -> slideInAnimation},
            exitTransition = {_,_ -> slideOutAnim}){
            DayDetailUI(navController = navController,viewModel = viewModel)
        }
    }
}


