package com.basket.sample.scango.presentation.feature.dashboard

import basketscanandgo.presentation.generated.resources.Res
import basketscanandgo.presentation.generated.resources.home
import basketscanandgo.presentation.generated.resources.home_border
import basketscanandgo.presentation.generated.resources.main_catalog_title
import basketscanandgo.presentation.generated.resources.main_home_title
import basketscanandgo.presentation.generated.resources.main_profile_title
import basketscanandgo.presentation.generated.resources.main_scango_title
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

sealed class DashboardNavigation(
    val route: String,
    val title: StringResource,
    val selectedIcon: DrawableResource,
    val unSelectedIcon: DrawableResource,
) {
    data object Overview : DashboardNavigation(
        route = "Home",
        title = Res.string.main_home_title,
        selectedIcon = Res.drawable.home,
        unSelectedIcon = Res.drawable.home_border,
    )

    data object Catalog : DashboardNavigation(
        route = "Catalog",
        title = Res.string.main_catalog_title,
        selectedIcon = Res.drawable.home,
        unSelectedIcon = Res.drawable.home_border,
    )

    data object ScanGo : DashboardNavigation(
        route = "ScanGo",
        title = Res.string.main_scango_title,
        selectedIcon = Res.drawable.home,
        unSelectedIcon = Res.drawable.home_border,
    )

    data object Profile : DashboardNavigation(
        route = "Profile",
        title = Res.string.main_profile_title,
        selectedIcon = Res.drawable.home,
        unSelectedIcon = Res.drawable.home_border,
    )
}
