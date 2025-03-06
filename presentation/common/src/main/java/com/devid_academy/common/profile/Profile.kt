package com.devid_academy.common.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.devid_academy.common.common.Background
import com.devid_academy.common.common.MediumButton
import org.koin.androidx.compose.getViewModel
import com.devid_academy.common.R
import com.devid_academy.ui.Route


@Composable
fun Profile(
    navController: NavHostController,
) {
    val viewModel = getViewModel<ProfileViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        when (uiState) {
            is ProfileUiState.LoggedOut -> {
                navController.popBackStack(Route.HomeScreen.name, false)

            }
            else -> { /* no-op */ }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getUserProfile()
    }

    when (val state = uiState) {
        is ProfileUiState.Success -> {
            ProfileContent(
                uiState = state,
                onLogout = {
                    viewModel.logout()
                }
            )
        }
        is ProfileUiState.Loading -> {
            // Show loading state
        }
        else -> { /* Handle other states */ }
    }
}

@Composable
fun ProfileContent(
    uiState: ProfileUiState.Success,
    onLogout: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Background()

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(1f))
            
            Text(
                text = "PROFIL",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(Modifier.height(32.dp))
            
            Text(
                text = "Pseudo: ${uiState.username}",
                fontSize = 18.sp
            )
            
            Text(
                text = "Email: ${uiState.email}",
                fontSize = 18.sp
            )
            
            Text(
                text = "Points totaux: ${uiState.totalPoints}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )


            MediumButton(
                label = stringResource(R.string.profile_button_label_logout),
                containerColor = Color.Red
            ) {
                onLogout()
            }

            Spacer(Modifier.weight(1f))
        }
    }
}

@Preview
@Composable
private fun ProfileContentPreview() {
    ProfileContent(
        ProfileUiState.Success(
            username = "User123",
            email = "user@example.com",
            totalPoints = 1000
        ),
        {}
    )
}