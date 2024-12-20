package com.example.shultetable.model

import androidx.compose.ui.graphics.Color

data class ColorCard(
    val id: Int,
    val color: Color,
    val isRevealed: Boolean = false,
    val isMatched: Boolean = false
)

data class ColorPairsState(
    val cards: List<ColorCard> = emptyList(),
    val score: Int = 0,
    val moves: Int = 0,
    val firstSelectedCard: Int? = null, // индекс первой выбранной карты
    val timeLeftInMillis: Long = 60000, // 60 секунд на игру
    val gameState: GameState = GameState.NotStarted
)
