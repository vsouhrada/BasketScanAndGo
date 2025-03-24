package com.basket.sample.scango.presentation.feature.scango.basket.overview.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import basketscanandgo.presentation.generated.resources.Res
import basketscanandgo.presentation.generated.resources.facebook
import basketscanandgo.presentation.generated.resources.google
import basketscanandgo.presentation.generated.resources.ic_shopping_basket
import com.basket.core.common.designSystem.compose.component.Spacer_12dp
import com.basket.core.common.designSystem.compose.component.Spacer_16dp
import com.basket.core.common.designSystem.compose.screen.Screen
import com.basket.sample.scango.presentation.feature.scango.basket.overview.screen.model.BasketItem
import com.basket.sample.scango.presentation.feature.scango.basket.overview.screen.state.BasketScreenActionState
import com.basket.sample.scango.presentation.feature.scango.basket.overview.screen.state.BasketScreenEvent
import com.basket.sample.scango.presentation.feature.scango.basket.overview.screen.state.BasketScreenState
import kotlinx.coroutines.flow.SharedFlow
import org.jetbrains.compose.resources.painterResource

class BasketScreen(
    val state: State<BasketScreenState>,
    val actionState: SharedFlow<BasketScreenActionState>,
    val onSendEvent: (BasketScreenEvent) -> Unit,
    val onNavigateToCheckout: () -> Unit,
) : Screen {
    @Composable
    override fun Render() {
        LaunchedEffect(Unit) {
            actionState.collect { action ->
                when (action) {
                    is BasketScreenActionState.StartCheckout -> {
                        onNavigateToCheckout()
                    }
                }
            }
        }

        /*  Column(modifier = Modifier.fillMaxWidth()) {
              Text(text = "Basket Overview....")
              Spacer_32dp()
              BasketScreen()
          }*/
        RenderBasketScreen()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun RenderBasketScreen() {
        val basketItems =
            remember {
                mutableStateListOf(
                    BasketItem("Apple", Res.drawable.facebook, 1.99, 1),
                    BasketItem("Banana", Res.drawable.google, 0.99, 2),
                    BasketItem("Orange", Res.drawable.ic_shopping_basket, 2.49, 1),
                )
            }

        val totalPrice = remember { mutableFloatStateOf(calculateTotal(basketItems)) }
        val searchText = remember { mutableStateOf("") }

        LaunchedEffect(basketItems) {
            snapshotFlow { basketItems.sumOf { it.price * it.quantity } }
                .collect { totalPrice.floatValue = it.toFloat() }
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // App Bar
            TopAppBar(title = { Text("My Basket") })

            Spacer_16dp()

            // Total Price Card
            Card(
                modifier = Modifier.fillMaxWidth().widthIn(max = 600.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text("Total Price", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                    AnimatedContent(
                        targetState = totalPrice.floatValue,
                        transitionSpec = {
                            slideInVertically() + fadeIn() togetherWith slideOutVertically() + fadeOut()
                        },
                    ) { price ->
                        Text("$price", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer_16dp()

            // Basket Item List
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f).widthIn(max = 600.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(basketItems) { item ->
                    BasketItemCard(item) { updatedItem ->
                        val index = basketItems.indexOf(item)
                        if (index != -1) {
                            basketItems[index] = updatedItem
                        }
                    }
                }
            }

            Spacer_16dp()

            // Search Area
            Row(
                modifier = Modifier.fillMaxWidth().widthIn(max = 600.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedTextField(
                    value = searchText.value,
                    onValueChange = { searchText.value = it },
                    label = { Text("Enter Item ID") },
                    modifier = Modifier.weight(1f),
                )

                Spacer_12dp()

                Button(
                    onClick = {
                        if (searchText.value.isNotBlank()) {
                            val newItem =
                                BasketItem("New Item", Res.drawable.ic_shopping_basket, 2.99, 1)
                            basketItems.add(newItem)
                            searchText.value = ""
                        }
                    },
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Item")
                }
            }
        }
    }

    @Composable
    fun BasketItemCard(item: BasketItem, onQuantityChange: (BasketItem) -> Unit,) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(4.dp),
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(item.image),
                    contentDescription = item.name,
                    modifier = Modifier.size(64.dp),
                )

                Spacer_12dp()

                Column(modifier = Modifier.weight(1f)) {
                    Text(text = item.name, fontSize = 18.sp)
                    Text(
                        text = "$${item.price}",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }

                Spacer_12dp()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        if (item.quantity > 0) {
                            onQuantityChange(item.copy(quantity = item.quantity - 1))
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Decrease Quantity",
                        )
                    }

                    Text(
                        text = "${item.quantity}",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(horizontal = 8.dp),
                    )

                    IconButton(onClick = {
                        onQuantityChange(item.copy(quantity = item.quantity + 1))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Increase Quantity",
                        )
                    }
                }
            }
        }
    }

    fun calculateTotal(items: List<BasketItem>): Float {
        return items.sumOf { it.price * it.quantity }.toFloat()
    }
}
