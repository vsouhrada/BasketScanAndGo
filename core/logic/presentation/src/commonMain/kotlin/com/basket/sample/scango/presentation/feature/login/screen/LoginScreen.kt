package com.basket.sample.scango.presentation.feature.login.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import basketscanandgo.presentation.generated.resources.Res
import basketscanandgo.presentation.generated.resources.ic_shopping_basket
import basketscanandgo.presentation.generated.resources.main_home_title
import basketscanandgo.presentation.generated.resources.sign_in
import com.basket.core.common.designSystem.compose.screen.Screen
import com.basket.core.common.designSystem.compose.theme.baseGradientBackground
import com.basket.core.common.designSystem.compose.theme.buttonGradientBackground
import com.basket.core.common.designSystem.uikit.widget.GradientButton
import com.basket.sample.scango.presentation.feature.login.model.UserCredentials
import com.basket.sample.scango.presentation.feature.login.screen.component.CustomInputField
import com.basket.sample.scango.presentation.feature.login.screen.state.LoginScreenActionState
import com.basket.sample.scango.presentation.feature.login.screen.state.LoginScreenEvent
import com.basket.sample.scango.presentation.feature.login.screen.state.LoginScreenState
import kotlinx.coroutines.flow.SharedFlow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class LoginScreen(
    val state: State<LoginScreenState>,
    val onSendEvent: (LoginScreenEvent) -> Unit,
    val actionState: SharedFlow<LoginScreenActionState>,
    val navigateToDashboard: () -> Unit,
    val navigateToRegister: () -> Unit,
    val navigateToSplash: () -> Unit
) : Screen {

    @Composable
    override fun Render() {
        LaunchedEffect(Unit) {
            actionState.collect { action ->
                when (action) {
                    LoginScreenActionState.UserAuthorized -> navigateToDashboard()
                }
            }
        }

        Scaffold(
            content = { paddingValues ->
                RenderData(

                )
            }
        )
    }

    @Composable
    private fun RenderData() {
        Surface(modifier = Modifier.fillMaxSize()) {
            val inputValueID = remember {
                mutableStateOf(state.value.data?.email?.value ?: "")
            }
            val inputValuePass = remember {
                mutableStateOf("")
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f)
                        .clip(
                            shape = RoundedCornerShape(
                                bottomStart = 25.dp,
                                bottomEnd = 25.dp
                            )
                        )
                        .background(
                            brush = baseGradientBackground()
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize(0.6f)
                            .offset(y = ((-20).dp)),
                        painter = painterResource(resource = Res.drawable.ic_shopping_basket),
                        contentDescription = "backgroundContentDescription",
                        contentScale = ContentScale.Fit
                    )
                }
                Column(
                    modifier = Modifier
                        .offset(y = -20.dp)
                        .width(290.dp).requiredHeight(360.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .width(290.dp),
                        shape = RoundedCornerShape(25.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 30.dp)
                                .padding(top = 30.dp, bottom = 50.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(Res.string.main_home_title),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            CustomInputField(inputValue = inputValueID, type = "uid")
                            CustomInputField(inputValue = inputValuePass, type = "password")
                        }
                    }

                    GradientButton(
                        modifier = Modifier.offset(y = (-24).dp).align(Alignment.CenterHorizontally),
                        text = stringResource(Res.string.sign_in),
                        textColor = Color.White,
                        gradient = buttonGradientBackground()
                    ) {
                        onSendEvent(
                            LoginScreenEvent.AuthorizeUser(
                                userCredentials = UserCredentials(
                                    userId = inputValueID.value,
                                    password = inputValuePass.value
                                )
                            )
                        )
                    }
                }
            }
        }

        /*val isError by viewModel.errorState.collectAsState()
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedVisibility(!isError) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "Self-Scanning",
                        fontSize = 30.sp
                    )

                    Spacer(modifier = Modifier.height(30.dp))
                    TextField(
                        value = name,
                        maxLines = 1,
                        label = { Text(text = "Enter your customer number") },

                        onValueChange = onTextChanged
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        onClick = {
                            onGoClicked(name)
                        }
                    ) {
                        Text(text = "Log In!")
                    }
                }
                Column(
                    // we are using column to align our
                    // imageview to center of the screen.
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    // below line is used for specifying
                    // vertical arrangement.
                    verticalArrangement = Arrangement.Top,
                    // below line is used for specifying
                    // horizontal arrangement.
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    ScaCircularProgressIndicator(showProgress)
                }
            }

            if (isError) {
                // ErrorSnackBar(syncFailedReason = "Some error message here")
                FullScreenError(
                    image = null,//imageResource("drawables/ic_error_code.png"),
                    title = "Authorization Failed!",
                    message = "Some error message here"
                )
            }
        }*/
    }
}
