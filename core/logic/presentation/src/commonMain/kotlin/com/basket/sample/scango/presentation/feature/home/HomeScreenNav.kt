package com.basket.sample.scango.presentation.feature.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.basket.sample.scango.presentation.feature.home.screen.HomeScreen
import com.basket.sample.scango.presentation.feature.home.viewmodel.HomeViewModel
import org.koin.compose.koinInject

@Composable
fun HomeScreenNav(logout: () -> Unit) {
    val navigator = rememberNavController()

    NavHost(
        startDestination = HomeScreenNavigation.Home.route,
        navController = navigator,
        modifier = Modifier.fillMaxSize(),
    ) {
        composable(route = HomeScreenNavigation.Home.route) {
            val viewModel: HomeViewModel = koinInject()
            HomeScreen(
                state =
                viewModel.state.collectAsStateWithLifecycle(
                    lifecycleOwner = LocalLifecycleOwner.current,
                ),
                onSendEvent = viewModel::sendScreenEvent,
                actionState = viewModel.actionState,
            ).Render()
        }

        composable(route = HomeScreenNavigation.StoreDetail.route) {
            TODO()
        }

        composable(route = HomeScreenNavigation.ItemDetail.route) {
            TODO()
        }

        composable(route = HomeScreenNavigation.Settings.route) {
            TODO()
        }
    }
}
