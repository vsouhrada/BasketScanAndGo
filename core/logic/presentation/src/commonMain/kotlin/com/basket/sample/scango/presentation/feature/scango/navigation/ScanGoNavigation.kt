package com.basket.sample.scango.presentation.feature.scango.navigation

import com.basket.sample.scango.presentation.navigation.ScreenNavigation
import kotlinx.serialization.Serializable

@Serializable
sealed class ScanGoNavigation(
    override val route: String,
    // override val arguments: List<@Contextual NamedNavArgument>
) : ScreenNavigation {
    @Serializable
    data object StartTrip : ScanGoNavigation(route = "StartTrip")

    @Serializable
    data object CreateBasket : ScanGoNavigation(route = "CreateBasket")

    @Serializable
    data class BasketOverview(val basketId: String) : ScanGoNavigation(route = "BasketOverview")
}
