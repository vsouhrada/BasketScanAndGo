package com.basket.sample.scango.presentation.feature.splash.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import basketscanandgosample.presentation.generated.resources.Res
import basketscanandgosample.presentation.generated.resources.app_name
import basketscanandgosample.presentation.generated.resources.header_linkedin_profile1
import com.basket.core.common.designSystem.compose.screen.Screen
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class SplashScreen(
    val navigateToDashboard: () -> Unit,
    val navigateToLogin: () -> Unit,
) : Screen {

    @Composable
    override fun Render() {
        LaunchedEffect(Unit/*state.navigateToMain*/) {
            delay(1500L)
            navigateToLogin()
            /*if (state.navigateToMain) {
                navigateToMain()
            } else {
                navigateToLogin()
            }*/
        }

        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(true) {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Make the Image fill the width of the parent
                    Image(
                        painter = painterResource(Res.drawable.header_linkedin_profile1),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth() // Make the image full-width
                            .aspectRatio(2.5f) // Optional: Adjust aspect ratio for better layout
                    )
                    Text(stringResource(Res.string.app_name))
                }
            }
        }

    }
}
