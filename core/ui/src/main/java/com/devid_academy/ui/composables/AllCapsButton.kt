package com.devid_academy.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devid_academy.core.ui.R

@Composable
fun AllCapsButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Button(
        onClick = { onClick() },
        modifier = modifier,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 4.dp,
            disabledElevation = 0.dp
        ),
        contentPadding = PaddingValues(16.dp, 0.dp),
        border = BorderStroke(width = 2.dp, Color(Color.Black.toArgb())),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFE6464),
            contentColor = Color(0xFFFFFFFF)
        )

    ) {
        Text(
            text = label,
            fontFamily = FontFamily(Font(R.font.kanit_regular)),
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp
        )
    }

}