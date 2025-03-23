package com.basket.sample.scango.presentation.navigation

sealed class AppNavigation(
    override val route: String,
    // override val arguments: List<NamedNavArgument>
) : ScreenNavigation {
    data object Splash : AppNavigation(route = "Splash")

    data object Dashboard : AppNavigation(route = "Dashboard")
}
