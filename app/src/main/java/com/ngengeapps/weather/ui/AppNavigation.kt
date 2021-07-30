package com.ngengeapps.weather.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation

sealed class Screen(val route:String) {
    object Home:Screen("home")
    object DayDetails:Screen("details")

}


@Composable
fun AppNavigation(
    navController: NavHostController,
    openSettings:()->Unit
) {
    NavHost(navController  =navController,startDestination = Screen.Home.route){
        composable(Screen.Home.route){

        }
    }
}


