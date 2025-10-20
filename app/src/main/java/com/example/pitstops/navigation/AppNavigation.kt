package com.example.pitstops.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pitstops.ui.addpitstop.RegistrarPitStopScreen
import com.example.pitstops.ui.listpitstops.PitStopListScreen
import com.example.pitstops.ui.principalView.PrincipalScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.FirstScreen.route){
        composable (route = AppScreens.FirstScreen.route){
            PrincipalScreen(navController)
        }
        composable (route = AppScreens.RegisterScreen.route){
            RegistrarPitStopScreen(navController)
        }
        composable (route = AppScreens.ListScreen.route){
            PitStopListScreen(navController)
        }
    }

}