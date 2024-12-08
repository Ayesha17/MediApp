package com.ayesha.mediapp.navigation

import com.ayesha.mediapp.navigation.RouteConstants.DETAIL_SCREEN
import com.ayesha.mediapp.navigation.RouteConstants.HOME
import com.ayesha.mediapp.navigation.RouteConstants.HOME_SCREEN
import com.ayesha.mediapp.navigation.RouteConstants.LOGIN
import com.ayesha.mediapp.navigation.RouteConstants.SPLASH

sealed class Route(val route: String) {

    data object Splash : Route(SPLASH)
    data object Login : Route(LOGIN)
    data object Home : Route(HOME) {
        data object Home : Route(HOME_SCREEN)
        data object Detail : Route(DETAIL_SCREEN)

    }
}