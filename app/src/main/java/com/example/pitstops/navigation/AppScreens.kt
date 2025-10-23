package com.example.pitstops.navigation

sealed class AppScreens (val route : String){
    object FirstScreen : AppScreens ("first_screen")
    object RegisterScreen : AppScreens ("register_screen")
    object ListScreen : AppScreens ("list_screen")

    object EditPitStop : AppScreens("edit_pitstop")
}