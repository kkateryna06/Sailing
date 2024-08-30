package com.example.myapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.CreateButton
import com.example.myapplication.CreateStatsText
import com.example.myapplication.R
import com.example.myapplication.isEnoughMoney


@Composable
fun CaptainGame() {
    val treasureFound = remember { mutableStateOf(0) }
    val direction = remember { mutableStateOf("North") }
    val shipHealth = remember { mutableStateOf(10) }
    val maxShipHealth = remember { mutableStateOf(10) }
    val shipLvl = remember { mutableStateOf(1) }
    val luckLvl = remember { mutableStateOf(1) }
    val journeyResult = remember { mutableStateOf("") }

    val treasuresSet = remember { mutableSetOf(-1, 1) }
    val imageAction = remember { mutableStateOf(R.drawable.ship) }

    var showLoose by remember { mutableStateOf(false) }
    var showNoMoney by remember { mutableStateOf(false) }
    val showHint = remember { mutableStateOf(true) }


    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 30.dp)) {

        CreateStatsText(statName = treasureFound, statScreenText = "Treasures Found")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 3.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Direction: ", fontSize = 19.sp)
            Text(text = direction.value, fontSize = 20.sp)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 3.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Ship's HP: ", fontSize = 19.sp)
            Text(text = "${shipHealth.value}/${maxShipHealth.value}", fontSize = 20.sp)
        }

        CreateStatsText(statName = shipLvl, statScreenText = "Ship level")
        CreateStatsText(statName = luckLvl, statScreenText = "Luck level")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = journeyResult.value, fontSize = 25.sp)
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(imageAction.value),
                contentDescription = "action",
                modifier = Modifier.size(200.dp, 230.dp)
            )
        }


        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Box(
                modifier = Modifier
                    .size(200.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(R.drawable.compass),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    CreateButton(compassDirection = "North")
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CreateButton(compassDirection = "West")
                        CreateButton(compassDirection = "East")
                    }
                    CreateButton(compassDirection = "South")
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Box(modifier = Modifier.padding(bottom = 10.dp)) {
                Button(
                    modifier = Modifier.size(150.dp, 50.dp),
                    onClick = {
                        if (isEnoughMoney(
                                treasureFound.value,
                                maxShipHealth.value - shipHealth.value
                            ) ) {
                            val hp = maxShipHealth.value - shipHealth.value
                            treasureFound.value -= hp
                            shipHealth.value += hp
                            imageAction.value = R.drawable.ship
                        }


                    }) {
                    Text(text = "REPAIR", fontSize = 20.sp)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    if (isEnoughMoney(treasureFound.value, 5)) {
                        treasureFound.value -= 5
                        shipLvl.value += 1
                        maxShipHealth.value += 5
                        imageAction.value = R.drawable.ship
                    }

                }) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "UPGRADE SHIP")
                        Text(text = "(5 coins)")
                    }
                }

                Spacer(modifier = Modifier.width(15.dp))

                Button(onClick = {
                    if (isEnoughMoney(treasureFound.value, 5)) {
                        treasureFound.value -= 5
                        luckLvl.value += 1
                        treasuresSet.add(luckLvl.value)
                        imageAction.value = R.drawable.ship
                    }
                }) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "UPGRADE LUCK")
                        Text(text = "(5 coins)")
                    }
                }
            }
        }
    }
}
