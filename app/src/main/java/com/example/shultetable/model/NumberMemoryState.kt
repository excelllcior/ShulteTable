package com.example.shultetable.model

data class NumberMemoryState(
    val currentNumber: String = "",
    val userInput: String = "",
    val score: Int = 0,
    val timeToMemorize: Long = 2000, // 2 секунды на запоминание числа
    val totalGameTime: Long = 60000, // 60 секунд на всю игру
    val timeLeftInMillis: Long = totalGameTime,
    val showNumber: Boolean = true, // флаг для показа/скрытия числа
    val gameState: GameState = GameState.NotStarted
)
