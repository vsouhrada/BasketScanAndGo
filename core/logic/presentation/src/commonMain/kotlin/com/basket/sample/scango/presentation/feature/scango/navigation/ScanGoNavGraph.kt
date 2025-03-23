package com.basket.sample.scango.presentation.feature.scango.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.basket.sample.scango.presentation.feature.scango.basket.create.screen.CreateBasketScreen
import com.basket.sample.scango.presentation.feature.scango.basket.create.viewmodel.CreateBasketViewModel
import com.basket.sample.scango.presentation.feature.scango.basket.overview.navigation.BasketNavGraph
import com.basket.sample.scango.presentation.feature.scango.startTrip.screen.StartTripScreen
import com.basket.sample.scango.presentation.feature.scango.startTrip.viewmodel.StartTripViewModel
import org.koin.compose.koinInject

@Composable
fun ScanGoNavGraph() {
    val navigator = rememberNavController()

    NavHost(
        startDestination = ScanGoNavigation.StartTrip,
        navController = navigator,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<ScanGoNavigation.StartTrip> { backStackEntry ->
            val viewModel: StartTripViewModel = koinInject()
            StartTripScreen(
                state = viewModel.state.collectAsStateWithLifecycle(
                    lifecycleOwner = LocalLifecycleOwner.current
                ),
                onSendEvent = viewModel::sendScreenEvent,
                actionState = viewModel.actionState,
                onNavigateToBeginShopping = { basketId ->
                    when (basketId) {
                        null -> navigator.navigate(ScanGoNavigation.CreateBasket)
                        else -> navigator.navigate(ScanGoNavigation.BasketOverview)
                    }
                }
            ).Render()
        }

        composable<ScanGoNavigation.CreateBasket> {
            val viewModel: CreateBasketViewModel = koinInject()
            CreateBasketScreen(
                state = viewModel.state.collectAsStateWithLifecycle(
                    lifecycleOwner = LocalLifecycleOwner.current
                ),
                onSendScreenEvent = viewModel::sendScreenEvent,
                actionState = viewModel.actionState,
                onNavigateToBasketOverview = { basketId ->
                    navigator.navigate(ScanGoNavigation.BasketOverview(basketId))}
            ).Render()
        }

        composable<ScanGoNavigation.BasketOverview> { backStackEntry ->
            BasketNavGraph(basketId = backStackEntry.toRoute<ScanGoNavigation.BasketOverview>().basketId)
        }
    }
}
