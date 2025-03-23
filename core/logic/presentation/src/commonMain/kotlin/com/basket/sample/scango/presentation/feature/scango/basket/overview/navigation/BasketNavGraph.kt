package com.basket.sample.scango.presentation.feature.scango.basket.overview.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.basket.sample.scango.presentation.feature.scango.basket.overview.screen.BasketScreen
import com.basket.sample.scango.presentation.feature.scango.basket.overview.screen.state.BasketScreenEvent
import com.basket.sample.scango.presentation.feature.scango.basket.overview.viewmodel.BasketViewModel
import org.koin.compose.koinInject

@Composable
fun BasketNavGraph(basketId: String) {
    val navigator = rememberNavController()

    NavHost(
        startDestination = BasketNavigation.BasketOverview,
        navController = navigator,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<BasketNavigation.BasketOverview> {
            val viewModel: BasketViewModel = koinInject()
            LaunchedEffect(basketId) {
                viewModel.sendScreenEvent(BasketScreenEvent.SetActiveBasket(basketId))
            }

            BasketScreen(
                state = viewModel.state.collectAsStateWithLifecycle(
                    lifecycleOwner = LocalLifecycleOwner.current
                ),
                onSendEvent = viewModel::sendScreenEvent,
                actionState = viewModel.actionState,
                onNavigateToCheckout = {
                    TODO()
                }
            ).Render()
        }
    }
}
