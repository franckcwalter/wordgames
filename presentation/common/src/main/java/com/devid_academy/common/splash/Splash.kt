package com.devid_academy.common.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.devid_academy.common.R
import com.devid_academy.ui.model.Route
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel


@Composable
fun SplashScreen(
    navController: NavHostController,
    innerPadding: PaddingValues
){

    val viewModel = getViewModel<SplashViewModel>()

    SplashContent()

    LaunchedEffect(Unit) {

        viewModel.getData()

        delay(3000)
        navController.navigate(Route.HomeScreen.name){
            popUpTo(Route.SplashScreen.name) { inclusive = true }
        }
    }

}

@Composable
fun SplashContent(){
    Box(
        Modifier.fillMaxSize()
            .background(Color(0xFFfecb36))
    ){
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = stringResource(R.string.image_content_desc_logo),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .align(Alignment.Center)
                .width(300.dp)
        )
    }
}

@Preview
@Composable
private fun SplashContentPreview() {
    SplashContent()
}