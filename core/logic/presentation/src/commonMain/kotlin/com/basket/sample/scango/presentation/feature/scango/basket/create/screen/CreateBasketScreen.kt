package com.basket.sample.scango.presentation.feature.scango.basket.create.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import basketscanandgosample.presentation.generated.resources.Res
import basketscanandgosample.presentation.generated.resources.ic_shopping_basket
import basketscanandgosample.presentation.generated.resources.main_scango_basket_create_button
import basketscanandgosample.presentation.generated.resources.main_scango_basket_create_info
import com.basket.core.common.designSystem.compose.component.Spacer_16dp
import com.basket.core.common.designSystem.compose.component.Spacer_32dp
import com.basket.core.common.designSystem.compose.screen.Screen
import com.basket.sample.scango.presentation.feature.scango.basket.create.screen.state.CreateBasketScreenActionState
import com.basket.sample.scango.presentation.feature.scango.basket.create.screen.state.CreateBasketScreenEvent
import com.basket.sample.scango.presentation.feature.scango.basket.create.screen.state.CreateBasketScreenState
import kotlinx.coroutines.flow.SharedFlow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class CreateBasketScreen(
    val state: State<CreateBasketScreenState>,
    val actionState: SharedFlow<CreateBasketScreenActionState>,
    val onSendScreenEvent: (CreateBasketScreenEvent) -> Unit,
    val onNavigateToBasketOverview: (String) -> Unit,
) : Screen {

    @Composable
    override fun Render() {
        LaunchedEffect(Unit) {
            actionState.collect { action ->
                when (action) {
                    is CreateBasketScreenActionState.BasketCreated -> {
                        onNavigateToBasketOverview(action.basketId)
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Big Icon
            Icon(
                painter = painterResource(Res.drawable.ic_shopping_basket), // Replace with your actual icon
                contentDescription = "Shopping Icon",
                tint = Color.Unspecified,
                modifier = Modifier.size(120.dp)
            )

            Spacer_16dp()

            // Text Information
            Text(
                text = stringResource(Res.string.main_scango_basket_create_info),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                maxLines = 2
            )

            Spacer_32dp()

            // Create Button
            Button(onClick = {
                onSendScreenEvent(CreateBasketScreenEvent.CreateBasketEvent)
            }) {
                Text(text = stringResource(Res.string.main_scango_basket_create_button))
            }
        }
    }
}
