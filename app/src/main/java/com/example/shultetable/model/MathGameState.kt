package com.example.shultetable.model

data class MathProblem(
    val expression: String,
    val correctAnswer: Int,
    val options: List<Int>
)

data class MathGameState(
    val currentProblem: MathProblem? = null,
    val score: Int = 0,
    val wrongAnswers: Int = 0,
    val timeLeftInMillis: Long = 60000,
    val gameState: GameState = GameState.NotStarted
)
