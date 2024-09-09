package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState


fun restartGame(
    treasureFound: MutableState<Int>,
    shipHealth: MutableState<Int>,
    maxShipHealth: MutableState<Int>,
    shipLvl: MutableState<Int>,
    luckLvl: MutableState<Int>,
    treasuresSet: MutableSet<Int>,
    imageAction: MutableState<Int>
) {
    treasureFound.value = 0
    shipHealth.value = 10
    maxShipHealth.value = 10
    shipLvl.value = 1
    luckLvl.value = 1

    treasuresSet.clear()
    treasuresSet.addAll(setOf(-1, 1))

    imageAction.value = R.drawable.ship
}


@Composable
fun isLose(
    playerStat: Int,
    showLose: MutableState<Boolean>,
    treasureFound: MutableState<Int>,
    shipHealth: MutableState<Int>,
    maxShipHealth: MutableState<Int>,
    shipLvl: MutableState<Int>,
    luckLvl: MutableState<Int>,
    treasuresSet: MutableSet<Int>,
    imageAction: MutableState<Int>
) {
    if (playerStat <= 0) {
        ShowLoseDialog(
            treasuresSet = treasuresSet,
            imageAction = imageAction,
            luckLvl = luckLvl,
            maxShipHealth = maxShipHealth,
            shipHealth = shipHealth,
            shipLvl = shipLvl,
            showLose = showLose,
            treasureFound = treasureFound
        )
    }
}


@Composable
fun isEnoughMoney(currentMoney: Int, price: Int): Boolean {
    return if (price > currentMoney) {
        ShowNoMoneyDialog(false)
        false
    }
    else {
        true
    }
}


@Composable
fun treasureOrDisaster(
    treasuresSet: MutableSet<Int>,
    journeyResult: MutableState<String>,
    treasureFound: MutableState<Int>,
    imageAction: MutableState<Int>,
    shipHealth: MutableState<Int>,
    showLose: MutableState<Boolean>,
    maxShipHealth: MutableState<Int>,
    shipLvl: MutableState<Int>,
    luckLvl: MutableState<Int>,
) {
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
        isLose(
            shipHealth.value,
            showLose,
            treasureFound,
            shipHealth,
            maxShipHealth,
            shipLvl,
            luckLvl,
            treasuresSet,
            imageAction
        )
    }
}
