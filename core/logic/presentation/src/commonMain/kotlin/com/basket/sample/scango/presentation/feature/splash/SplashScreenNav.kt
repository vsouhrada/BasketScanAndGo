package com.basket.sample.scango.presentation.feature.splash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.basket.sample.scango.presentation.feature.login.screen.LoginScreen
import com.basket.sample.scango.presentation.feature.login.viewmodel.LoginViewModel
import com.basket.sample.scango.presentation.feature.splash.SplashNavigation
import com.basket.sample.scango.presentation.feature.splash.screen.SplashScreen
import org.koin.compose.koinInject

@Composable
fun SplashScreenNav(navigateToMain: () -> Unit) {
    val navigator = rememberNavController()

    NavHost(
        startDestination = SplashNavigation.Splash.route,
        navController = navigator,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = SplashNavigation.Splash.route) {
            SplashScreen(
                navigateToDashboard = navigateToMain,
                navigateToLogin = {
                    navigator.navigate(SplashNavigation.Login.route)
                }
            ).Render()
        }
        composable(route = SplashNavigation.Login.route) {
            val viewModel: LoginViewModel = koinInject()
            LoginScreen(
                state = viewModel.state.collectAsStateWithLifecycle(
                    lifecycleOwner = LocalLifecycleOwner.current
                ),
                onSendEvent = viewModel::sendScreenEvent,
                actionState = viewModel.actionState,
                navigateToDashboard = navigateToMain,
                navigateToRegister = { navigator.navigate(SplashNavigation.Register.route) },
                navigateToSplash = { navigator.popBackStack() }
            ).Render()
        }
    }
}
