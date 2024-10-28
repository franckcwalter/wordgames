package com.devid_academy.common.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SquareIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector? = null,
    @DrawableRes imageResId: Int? = null,
    contentColor: Color = Color.Black,
    containerColor: Color = Color.White,
    onClick: ()-> Unit,
){
    Button(
        onClick = onClick,
        modifier = Modifier
            .then(
                modifier
            )
           // .padding(end = 12.dp)
            .size(48.dp)
         ,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = contentColor,
            containerColor = containerColor,
        ),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(2.dp, Color.Black)
    ){

        imageVector?.let{
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = it,
                contentDescription = null
            )
        }
        imageResId?.let {
            Image(
                modifier =  Modifier.size(24.dp),
                painter = painterResource(imageResId),
                contentDescription = null
            )
        }
    }

}