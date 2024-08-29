package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CaptainGame()
                }
            }
        }
    }
}

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

    fun restartGame() {
        treasureFound.value = 0
        shipHealth.value = 10
        maxShipHealth.value = 10
        shipLvl.value = 1
        luckLvl.value = 1

        treasuresSet.clear()
        treasuresSet.addAll(setOf(-1, 1))

        imageAction.value = R.drawable.ship
    }

    var showLoose by remember { mutableStateOf(false) }
    if (showLoose) {
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
                            showLoose = false
                            restartGame()
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


    fun isBroke(playerStat: Int) {
        if (playerStat <= 0) {
            showLoose = true
        }
    }


    var showNoMoney by remember { mutableStateOf(false) }
    if (showNoMoney) {
        Dialog(onDismissRequest = { showNoMoney = false }) {
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
                    Button(onClick = { showNoMoney = false }, modifier = Modifier.padding
                        (top = 10.dp)) {
                        Text(text = "OK")
                    }
                }
            }
        }
    }

    fun isEnoughMoney(currentMoney: Int, price: Int): Boolean {
        return if (price > currentMoney) {
            showNoMoney = true
            false
        }
        else {
            true
        }
    }

    /*GAME LOGIC*/
    fun treasureOrDisaster() {
        val treasure = treasuresSet.random()
        if (treasure != -1) {
            journeyResult.value = "You found a treasure"
            treasureFound.value += treasure
            imageAction.value = R.drawable.treasures
        }
        else {
            journeyResult.value = "You get to a storm"
            shipHealth.value -= 1
            imageAction.value = R.drawable.wave
            isBroke(shipHealth.value)
        }
    }
    /*GAME LOGIC*/

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
    fun CreateButton(compassDirection: String) {
        Button(
            modifier = Modifier
                .size(70.dp, 70.dp),
            onClick = {
                direction.value = compassDirection
                treasureOrDisaster()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            )
        ) {
            Text(text = "", fontSize = 18.sp)
        }
    }

    /*POP-UP MESSAGE, HINT*/
    val showHint = remember { mutableStateOf(true) }

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



    /*POP-UP MESSAGE, HINT*/


        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 30.dp)) {

            /* GAME STATS */
            /* GAME STATS */
            /* GAME STATS */


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

            /* GAME STATS */
            /* GAME STATS */
            /* GAME STATS */


            /* GAME ACTION */
            /* GAME ACTION */
            /* GAME ACTION */

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

            /* GAME ACTION */
            /* GAME ACTION */
            /* GAME ACTION */


            /* GAME BUTTONS */
            /* GAME BUTTONS */
            /* GAME BUTTONS */

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                /* COMPASS BUTTONS */
                /* COMPASS BUTTONS */

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

                /* COMPASS BUTTONS */
                /* COMPASS BUTTONS */


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

            /* GAME BUTTONS */
            /* GAME BUTTONS */
            /* GAME BUTTONS */

        }
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    CaptainGame()
}
