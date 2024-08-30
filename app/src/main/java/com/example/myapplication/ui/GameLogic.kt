package com.example.myapplication

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


fun isBroke(playerStat: Int) {
    if (playerStat <= 0) {
        showLoose = true
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
