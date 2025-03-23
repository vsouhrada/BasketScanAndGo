package com.basket.sample.scango.presentation.feature.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import basketscanandgosample.presentation.generated.resources.Res
import basketscanandgosample.presentation.generated.resources.main_home_special_for_you
import com.basket.core.common.designSystem.compose.component.Spacer_8dp
import com.basket.core.common.designSystem.compose.screen.Screen
import com.basket.core.common.designSystem.compose.theme.BasketTheme
import com.basket.sample.scango.presentation.feature.home.screen.state.HomeScreenActionState
import com.basket.sample.scango.presentation.feature.home.screen.state.HomeScreenEvent
import com.basket.sample.scango.presentation.feature.home.screen.state.HomeScreenState
import kotlinx.coroutines.flow.SharedFlow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

class HomeScreen(
    state: State<HomeScreenState>,
    onSendEvent: (HomeScreenEvent) -> Unit,
    actionState: SharedFlow<HomeScreenActionState>
) : Screen {

    @Composable
    override fun Render() {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {

                Spacer_8dp()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        // TODO Store
                    }
                }
            }

            Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {


                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        stringResource(Res.string.main_home_special_for_you),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }

    @Preview
    @Composable
    fun HomeScreenPreview() {
        BasketTheme {
            Render()
        }
    }

}
