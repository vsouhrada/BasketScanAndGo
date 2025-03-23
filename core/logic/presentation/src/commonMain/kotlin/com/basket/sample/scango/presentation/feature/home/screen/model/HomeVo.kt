package com.basket.sample.scango.presentation.feature.home.screen.model

import com.basket.core.common.designSystem.uikit.screen.DataModel
import com.basket.core.common.designSystem.uikit.widget.Field

data class HomeVo(
    val storeOverview: StoreOverviewVo
) : DataModel()

data class StoreOverviewVo(
    val name: Field<String>,
    val closingTime: Field<String>,
)
