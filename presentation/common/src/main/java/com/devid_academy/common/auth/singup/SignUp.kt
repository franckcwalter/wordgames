package com.devid_academy.common.auth.singup

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.devid_academy.auth.SignupDto
import com.devid_academy.common.R
import com.devid_academy.common.common.AuthTextField
import com.devid_academy.common.common.Background
import com.devid_academy.common.common.LargeButton
import com.devid_academy.common.common.MediumButton
import com.devid_academy.common.common.SquareIconButton
import com.devid_academy.ui.Route
import org.koin.androidx.compose.getViewModel


@Composable
fun SignupScreen(
    navController: NavHostController
) {
    val viewModel = getViewModel<SignupViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        when (uiState) {
            is SignupUiState.Success -> {
                navController.navigate(Route.HomeScreen.name) {
                    popUpTo(Route.HomeScreen.name) {
                        inclusive = true
                    }
                }
            }
            else -> { /* no-op */ }
        }
    }

    SignupContent(
        onSignupClick = { signupDto ->
            viewModel.signup(signupDto)
        },
        onGoToLogin = {
            navController.navigate(Route.LoginScreen.name)
        },
        onBack = {
            navController.popBackStack(route = Route.HomeScreen.name, false)
        }
    )
}

@Composable
fun SignupContent(

    onSignupClick: (SignupDto) -> Unit,
    onGoToLogin: () -> Unit,
    onBack: () -> Unit
) {

    val email = remember { mutableStateOf("") }
    val pseudo = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

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
                text = "CRÉER UN COMPTE",
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
                value = pseudo.value,
                onValueChange = { pseudo.value = it },
                label = "Pseudo",
                keyboardType = KeyboardType.Text,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            AuthTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = "Mot de passe",
                isPassword = true,
                keyboardType = KeyboardType.Password,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            AuthTextField(
                value = confirmPassword.value,
                onValueChange = { confirmPassword.value = it },
                label = "Confirmer le mot de passe",
                isPassword = true,
                keyboardType = KeyboardType.Password
            )
            Spacer(modifier = Modifier.height(50.dp))
            LargeButton(
                label = "CRÉER UN COMPTE",
                containerColor = Color(0xFFfecb36)
            ) {
                if (email.value.isEmpty() ||
                    pseudo.value.isEmpty() ||
                    password.value.isEmpty() ||
                    confirmPassword.value.isEmpty()
                ){
                    Toast.makeText(
                        context,
                        "Veuillez remplir tous les champs",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                if(password.value != confirmPassword.value){
                    Toast.makeText(
                        context,
                        "Les mots de passe doivent être identiques",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    onSignupClick(
                        SignupDto(
                            null,
                            pseudo.value,
                            email.value,
                            password.value
                        )
                    )
                }
            }
            MediumButton(
                label = "Déjà inscrit(e) ?",
                containerColor = Color(0xFFfe6465)
            ) {
                onGoToLogin()
            }

            Spacer(Modifier.weight(1f))
        }
    }
}

@Preview
@Composable
private fun SignupContentPreview() {
    SignupContent(
        // SignupUiState(),
        onSignupClick = { _ -> },
        onGoToLogin = { },
        onBack = {

        }
    )
}