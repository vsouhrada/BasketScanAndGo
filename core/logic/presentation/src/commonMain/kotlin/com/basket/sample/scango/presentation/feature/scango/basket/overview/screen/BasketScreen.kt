package com.basket.sample.scango.presentation.feature.scango.basket.overview.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
import com.basket.core.common.designSystem.compose.theme.ColorPalette
import com.basket.core.common.designSystem.compose.theme.headerGradientBackground
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
            modifier = Modifier.fillMaxSize().background(ColorPalette.Surface),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Gradient Header
            BasketHeader(
                itemCount = basketItems.size,
                totalPrice = totalPrice.floatValue,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Basket Item List
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp),
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
    fun BasketHeader(itemCount: Int, totalPrice: Float) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 4.dp)
                .background(headerGradientBackground())
                .padding(horizontal = 24.dp, vertical = 20.dp),
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "$itemCount items",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 14.sp,
                        )
                    }

                    // Cart Icon
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = Color.White.copy(alpha = 0.2f),
                                shape = CircleShape,
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Total price row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Total Price",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = (-0.5).sp,
                    )

                    AnimatedContent(
                        targetState = totalPrice,
                        transitionSpec = {
                            slideInVertically(
                                initialOffsetY = { -20 },
                                animationSpec = spring(Spring.StiffnessMediumLow),
                            ) + fadeIn() togetherWith
                                slideOutVertically(
                                    targetOffsetY = { 20 },
                                    animationSpec = spring(Spring.StiffnessMediumLow),
                                ) + fadeOut()
                        },
                        label = "header_total_price_animation",
                    ) { price: Float ->
                        Text(
                            text = "$$price",
                            color = Color.White,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun BasketItemCard(item: BasketItem, onQuantityChange: (BasketItem) -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(16.dp),
                ),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Brand/Logo placeholder
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = ColorPalette.Surface,
                            shape = RoundedCornerShape(12.dp),
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(item.image),
                        contentDescription = item.name,
                        modifier = Modifier.size(32.dp),
                    )
                }

                Spacer_16dp()

                // Item details
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ColorPalette.Black,
                    )
                    Spacer_12dp()
                    Text(
                        text = "$${item.price}",
                        fontSize = 14.sp,
                        color = ColorPalette.Grey,
                    )
                }

                Spacer_16dp()

                // Quantity Pill Control
                Row(
                    modifier = Modifier
                        .background(
                            color = ColorPalette.Surface.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(20.dp),
                        )
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = {
                            if (item.quantity > 0) {
                                onQuantityChange(item.copy(quantity = item.quantity - 1))
                            }
                        },
                        modifier = Modifier.size(32.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Decrease Quantity",
                            modifier = Modifier.size(16.dp),
                            tint = if (item.quantity > 0) {
                                ColorPalette.PrimaryColor
                            } else {
                                ColorPalette.Grey
                            },
                        )
                    }

                    Text(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        text = "${item.quantity}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = ColorPalette.Black,
                    )

                    IconButton(
                        onClick = {
                            onQuantityChange(item.copy(quantity = item.quantity + 1))
                        },
                        modifier = Modifier.size(32.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Increase Quantity",
                            modifier = Modifier.size(16.dp),
                            tint = ColorPalette.PrimaryColor,
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
