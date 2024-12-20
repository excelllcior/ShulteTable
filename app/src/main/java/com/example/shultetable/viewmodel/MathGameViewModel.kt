package com.example.shultetable.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shultetable.model.GameState
import com.example.shultetable.model.MathGameState
import com.example.shultetable.model.MathProblem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class MathGameViewModel : ViewModel() {
    private val _state = MutableStateFlow(MathGameState())
    val state: StateFlow<MathGameState> = _state

    private var timerJob: Job? = null

    fun startNewGame() {
        timerJob?.cancel()
        _state.value = MathGameState(
            currentProblem = generateProblem(),
            score = 0,
            wrongAnswers = 0,
            timeLeftInMillis = 60 * 1000,
            gameState = GameState.Playing
        )
        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_state.value.timeLeftInMillis > 0) {
                delay(100)
                _state.update { currentState ->
                    currentState.copy(
                        timeLeftInMillis = (currentState.timeLeftInMillis - 100).coerceAtLeast(0)
                    )
                }
            }
            _state.update { it.copy(gameState = GameState.Lost) }
        }
    }

    fun checkAnswer(answer: Int) {
        if (_state.value.gameState != GameState.Playing) return

        val currentProblem = _state.value.currentProblem ?: return
        if (answer == currentProblem.correctAnswer) {
            _state.update { 
                it.copy(
                    score = it.score + 1,
                    currentProblem = generateProblem()
                )
            }
        } else {
            _state.update {
                it.copy(
                    wrongAnswers = it.wrongAnswers + 1,
                    currentProblem = generateProblem()
                )
            }
        }
    }

    private fun generateProblem(): MathProblem {
        val operations = listOf(
            Triple("+", { a: Int, b: Int -> a + b }, 50),
            Triple("-", { a: Int, b: Int -> a - b }, 50),
            Triple("×", { a: Int, b: Int -> a * b }, 10)
        )

        val operation = operations.random()
        val maxNumber = operation.third
        val a = Random.nextInt(1, maxNumber)
        val b = Random.nextInt(1, maxNumber)

        val correctAnswer = operation.second(a, b)
        val options = generateOptions(correctAnswer)

        return MathProblem(
            expression = "$a ${operation.first} $b = ?",
            correctAnswer = correctAnswer,
            options = options
        )
    }

    private fun generateOptions(correctAnswer: Int): List<Int> {
        val options = mutableSetOf(correctAnswer)
        while (options.size < 4) {
            val offset = Random.nextInt(-10, 11)
            if (offset != 0) {
                options.add(correctAnswer + offset)
            }
        }
        return options.toList().shuffled()
    }

    fun pauseGame() {
        if (_state.value.gameState == GameState.Playing) {
            _state.update { it.copy(gameState = GameState.Paused) }
            timerJob?.cancel()
        }
    }

    fun resumeGame() {
        if (_state.value.gameState == GameState.Paused) {
            _state.update { it.copy(gameState = GameState.Playing) }
            startTimer()
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
