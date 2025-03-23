package com.basket.sample.scango.presentation.feature.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.basket.core.common.designSystem.compose.theme.DefaultNavigationBarItemTheme
import com.basket.sample.scango.presentation.feature.home.HomeScreenNav
import com.basket.sample.scango.presentation.feature.scango.navigation.ScanGoNavGraph
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun DashboardScreenNav(logout: () -> Unit) {

    val navBottomBarController = rememberNavController()
    // ChangeStatusBarColors(Color.White)
    Scaffold(bottomBar = {
        BottomNavigationUI(navController = navBottomBarController)
    }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                startDestination = DashboardNavigation.Overview.route,
                navController = navBottomBarController,
                modifier = Modifier.fillMaxSize()
            ) {
                composable(route = DashboardNavigation.Overview.route) {
                    HomeScreenNav(logout = logout)
                }
                composable(route = DashboardNavigation.Catalog.route) {
                    //  WishlistNav()
                }
                composable(route = DashboardNavigation.ScanGo.route) {
                    ScanGoNavGraph()
                }
                composable(route = DashboardNavigation.Profile.route) {
                    //  ProfileNav(logout = logout)
                }
            }
        }
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun BottomNavigationUI(
    navController: NavController,
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp
        )
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.background,
            tonalElevation = 8.dp
        ) {

            val items = listOf(
                DashboardNavigation.Overview,
                DashboardNavigation.Catalog,
                DashboardNavigation.ScanGo,
                DashboardNavigation.Profile,
            )
            items.forEach {
                val titleText = stringResource(it.title)
                NavigationBarItem(
                    label = { Text(text = titleText) },
                    colors = DefaultNavigationBarItemTheme(),
                    selected = it.route == currentRoute,
                    icon = {
                        Icon(
                            painter = painterResource(if (it.route == currentRoute) it.selectedIcon else it.unSelectedIcon),
                            contentDescription = titleText
                        )
                    },
                    onClick = {
                        if (currentRoute != it.route) {
                            navController.navigate(it.route) {
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    })
            }
        }
    }
}
