package com.ayesha.mediapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ayesha.mediapp.ui.home.view.HomeScreen
import com.ayesha.mediapp.ui.login.view.LoginScreen
import com.ayesha.mediapp.ui.splash.view.SplashScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Route.Splash.route,
    paddingValues: PaddingValues
) {
    Box(modifier = Modifier.padding(paddingValues)) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(route = Route.Splash.route) {
                SplashScreen {isLoggedIn->
                    if (isLoggedIn){
                        navController.navigate(Route.Home.Home.route){
                            popUpTo(Route.Splash.route) { inclusive = true }
                        }
                    }else {
                        navController.navigate(Route.Login.route) {
                            popUpTo(Route.Splash.route) { inclusive = true }
                        }
                    }
                }
            }
            composable(route = Route.Login.route) {
                LoginScreen{
                    navController.navigate(Route.Home.Home.route){
                        popUpTo(Route.Login.route) { inclusive = true }
                    }
                }
            }
            composable(route = Route.Home.Home.route) {
                HomeScreen()
            }
        }
    }
}