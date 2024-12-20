package com.example.shultetable.model

sealed class GameState {
    object NotStarted : GameState()
    object Playing : GameState()
    object Paused : GameState()
    object Won : GameState()
    object Lost : GameState()
}

data class ShulteTableState(
    val numbers: List<Int> = (1..25).shuffled(),
    val currentNumber: Int = 1,
    val timeLeftInMillis: Long = 60000,
    val gameState: GameState = GameState.NotStarted
)
