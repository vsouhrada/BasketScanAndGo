package com.basket.sample.scango.presentation.feature.scango.startTrip.screen

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
import basketscanandgo.presentation.generated.resources.Res
import basketscanandgo.presentation.generated.resources.ic_shopping_basket
import basketscanandgo.presentation.generated.resources.main_scango_start_begin
import basketscanandgo.presentation.generated.resources.main_scango_start_info
import com.basket.core.common.designSystem.compose.component.Spacer_16dp
import com.basket.core.common.designSystem.compose.component.Spacer_32dp
import com.basket.core.common.designSystem.compose.screen.Screen
import com.basket.sample.scango.presentation.feature.scango.startTrip.screen.state.StartTripActionState
import com.basket.sample.scango.presentation.feature.scango.startTrip.screen.state.StartTripScreenEvent
import com.basket.sample.scango.presentation.feature.scango.startTrip.screen.state.StartTripScreenState
import kotlinx.coroutines.flow.SharedFlow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class StartTripScreen(
    val state: State<StartTripScreenState>,
    val onSendEvent: (StartTripScreenEvent) -> Unit,
    val actionState: SharedFlow<StartTripActionState>,
    val onNavigateToBeginShopping: (String?) -> Unit,
) : Screen {

    @Composable
    override fun Render() {
        LaunchedEffect(Unit) {
            actionState.collect { action ->
                when (action) {
                    is StartTripActionState.BeginShopping -> {
                        onNavigateToBeginShopping(action.basketId)
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
                text = stringResource(Res.string.main_scango_start_info),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                maxLines = 2
            )

            Spacer_32dp()

            // Begin Shopping Button
            Button(onClick = {
                onSendEvent(StartTripScreenEvent.OnBeginShoppingEvent)
            }) {
                Text(text = stringResource(Res.string.main_scango_start_begin))
            }
        }
    }
}
