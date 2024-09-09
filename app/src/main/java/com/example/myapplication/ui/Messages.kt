package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog



@Composable
fun ShowLoseDialog(
    showLose: MutableState<Boolean>,
    treasureFound: MutableState<Int>,
    shipHealth: MutableState<Int>,
    maxShipHealth: MutableState<Int>,
    shipLvl: MutableState<Int>,
    luckLvl: MutableState<Int>,
    treasuresSet: MutableSet<Int>,
    imageAction: MutableState<Int>
) {
    Dialog(onDismissRequest = {  }) {
        Card(modifier = Modifier.padding(horizontal = 30.dp, )) {
            Column(
                modifier = Modifier.padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(250.dp),
                    painter = painterResource(R.drawable.pirates),
                    contentDescription = ""
                )
                Text(
                    text = "Wait... WHAT?! Is that a pirate ship? Oh no, this is a big problem...",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Start Again",
                    fontSize = 15.sp,
                    modifier = Modifier.padding(top = 15.dp, bottom = 5.dp)
                )
                Button(
                    onClick = {
                        showLose.value = false
                        restartGame(
                            treasureFound,
                            shipHealth,
                            maxShipHealth,
                            shipLvl,
                            luckLvl,
                            treasuresSet,
                            imageAction
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme
                        .colorScheme.error)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "")
                }
            }
        }
    }
}



@Composable
fun ShowNoMoneyDialog(showNoMoney: Boolean) {
    var showDialog = showNoMoney
    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Card(modifier = Modifier.padding(horizontal = 30.dp)) {
                Column(modifier = Modifier.padding(15.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Box() {
                        Image(
                            painter = painterResource(R.drawable.coins),
                            contentDescription = null,
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                    Text(
                        text = "You don't have enough money",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                    Button(onClick = { showDialog = false }, modifier = Modifier.padding
                        (top = 10.dp)) {
                        Text(text = "OK")
                    }
                }
            }
        }
    }
}



@Composable
fun ShowHintDialog(showHint: MutableState<Boolean>) {
    if (showHint.value) {
        Dialog(onDismissRequest = { showHint.value = false }) {
            Card(modifier = Modifier.padding(horizontal = 30.dp), colors = CardDefaults
                .cardColors(containerColor = Color.White.copy(alpha = 0.8f))) {
                Box(modifier = Modifier.padding(15.dp)) {
                    Text(
                        text = "Tap the compass to choose a direction for the ship",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


