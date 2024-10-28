package com.devid_academy.common.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.devid_academy.common.utils.getRandomColor
import com.devid_academy.core.ui.R

@Composable
fun Background(){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            getRandomColor()
        )
    ){
        Column(Modifier.fillMaxSize()) {
            repeat(3) {
                Image(
                    painter = painterResource(id = R.drawable.app_bg),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .alpha(0.2f),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}