package com.devid_academy.common.auth.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.devid_academy.auth.LoginDto
import com.devid_academy.common.R
import com.devid_academy.common.common.AuthTextField
import com.devid_academy.common.common.Background
import com.devid_academy.common.common.LargeButton
import com.devid_academy.common.common.MediumButton
import com.devid_academy.common.common.SquareIconButton
import com.devid_academy.ui.Route
import org.koin.androidx.compose.getViewModel


@Composable
fun LoginScreen(
    navController: NavHostController
) {

    val viewModel = getViewModel<LoginViewModel>()

    LoginContent(
        onLoginClick = { loginDto ->
            viewModel.login(loginDto)
        },
        onGoToSignup = {
            navController.navigate(Route.SignupScreen.name)
        },
        onBack = {
            navController.popBackStack(route = Route.HomeScreen.name, false)
        }
    )
}


@Composable
fun LoginContent(
    onLoginClick: (LoginDto) -> Unit,
    onGoToSignup: () -> Unit,
    onBack: () -> Unit
) {
    Box {
        Background()

        val email = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }

        val context = LocalContext.current

        Box {
            Background()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 30.dp).fillMaxHeight()
            ){
                Box(Modifier.padding(top = 33.dp).fillMaxWidth()) {
                    SquareIconButton(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        onClick = onBack,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    Image(painter = painterResource(id = R.drawable.logo),
                        contentDescription = stringResource(R.string.image_content_desc_logo),
                        modifier = Modifier
                            .align(Center)
                            .height(48.dp),
                        contentScale = ContentScale.FillHeight
                    )
                }
                Spacer(Modifier.weight(1f))
                Text(
                    text = "CONNEXION",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(50.dp))
                AuthTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = "Email",
                    keyboardType = KeyboardType.Email,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                AuthTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = "Mot de passe",
                    isPassword = true,
                    keyboardType = KeyboardType.Password,
                )
                Spacer(modifier = Modifier.height(50.dp))
                LargeButton(
                    label = "SE CONNECTER",
                    containerColor = Color(0xFFfecb36)
                ) {
                    if(email.value.isNotEmpty() || password.value.isNotEmpty())
                        onLoginClick(LoginDto(email.value, password.value))
                    else
                        Toast.makeText(
                            context,
                            "Veuillez remplir tous les champs",
                            Toast.LENGTH_LONG
                        ).show()
                }
                MediumButton(
                    label = "Pas encore inscrit(e) ?",
                    containerColor = Color(0xFFfe6465)
                ) {
                    onGoToSignup()
                }

                Spacer(Modifier.weight(1f))
            }
        }

    }
}




@Preview
@Composable
private fun LoginContentPreview() {
    LoginContent(
        // uiState = LoginUiState(),
        onLoginClick = {_-> },
        onGoToSignup = { },
        onBack = { }
    )
}
