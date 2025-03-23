package com.basket.sample.scango.presentation.feature.home

import com.basket.sample.scango.presentation.navigation.ScreenNavigation

sealed class HomeScreenNavigation(
    override val route: String,
    // override val arguments: List<NamedNavArgument>
) : ScreenNavigation {

    data object Home : HomeScreenNavigation(route = "Home")

    data object Settings : HomeScreenNavigation(route = "Settings")

    data object StoreDetail : HomeScreenNavigation(route = "StoreDetail")

    data object ItemDetail : HomeScreenNavigation(route = "ItemDetail")
}
