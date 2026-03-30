package com.basket.sample.scango

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.basket.core.common.designSystem.compose.theme.BasketTheme
import com.basket.sample.scango.di.allModules
import com.basket.sample.scango.presentation.feature.dashboard.DashboardScreenNav
import com.basket.sample.scango.presentation.feature.splash.SplashScreenNav
import com.basket.sample.scango.presentation.navigation.AppNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        configureKoin(this)
        modules(allModules)
    }) {
        BasketTheme {
            val navigator = rememberNavController()

            Box(modifier = Modifier.fillMaxSize()) {
                NavHost(
                    navController = navigator,
                    startDestination = AppNavigation.Splash.route,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    composable(route = AppNavigation.Splash.route) {
                        SplashScreenNav(navigateToMain = {
                            navigator.popBackStack()
                            navigator.navigate(AppNavigation.Dashboard.route)
                        })
                    }
                    composable(route = AppNavigation.Dashboard.route) {
                        DashboardScreenNav {
                            navigator.popBackStack()
                            navigator.navigate(AppNavigation.Splash.route)
                        }
                    }
                }
            }
        }
        /*MaterialTheme {
            var showContent by remember { mutableStateOf(false) }
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = { showContent = !showContent }) {
                    Text("Click me!")
                }
                AnimatedVisibility(showContent) {
                    val greeting = remember { Greeting().greet() }
                    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painterResource(Res.drawable.compose_multiplatform), null)
                        Text("Compose: $greeting")
                    }
                }
            }
        }*/
    }
}
