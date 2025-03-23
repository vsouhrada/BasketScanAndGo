package com.basket.sample.scango.presentation.feature.scango.basket.overview.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface BasketNavigation {
    @Serializable
    data object BasketOverview : BasketNavigation
}
