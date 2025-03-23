package com.basket.sample.scango.presentation.feature.scango.basket.overview.screen.model

import com.basket.core.common.designSystem.uikit.screen.DataModel
import com.basket.core.common.designSystem.uikit.widget.Field
import org.jetbrains.compose.resources.DrawableResource

data class BasketVo(
    val todo: Field<String>,
) : DataModel()

// Data Model
data class BasketItem(
    val name: String,
    val image: DrawableResource,
    val price: Double,
    val quantity: Int,
)
