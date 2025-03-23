package com.basket.sample.scango.presentation.feature.splash

import com.basket.sample.scango.presentation.navigation.ScreenNavigation

sealed class SplashNavigation(
    override val route: String,
//    override val arguments: List<NamedNavArgument>
) : ScreenNavigation {

    data object Splash : SplashNavigation(route = "Splash")

    data object Login : SplashNavigation(route = "Login")

    data object Register : SplashNavigation(route = "Register")
}
