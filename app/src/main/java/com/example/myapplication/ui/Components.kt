package com.example.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CreateStatsText(statName: MutableState<Int>, statScreenText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$statScreenText: ", fontSize = 19.sp)
        Text(text = "${statName.value}", fontSize = 20.sp)
    }
}

@Composable
fun CreateCompassButton(
    compassDirection: String,
    direction: MutableState<String>, onAction: () -> Unit
) {
    Button(
        modifier = Modifier
            .size(70.dp, 70.dp),
        onClick = {
            direction.value = compassDirection
            onAction()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black
        )
    ) {
        Text(text = "", fontSize = 18.sp)
    }
}
