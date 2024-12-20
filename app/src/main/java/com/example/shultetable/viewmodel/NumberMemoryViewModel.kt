package com.example.shultetable.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shultetable.model.GameState
import com.example.shultetable.model.NumberMemoryState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class NumberMemoryViewModel : ViewModel() {
    private val _state = MutableStateFlow(NumberMemoryState())
    val state: StateFlow<NumberMemoryState> = _state.asStateFlow()

    private var gameTimerJob: Job? = null
    private var numberTimerJob: Job? = null
    private var previousTimeLeft: Long = 0

    fun startNewGame() {
        gameTimerJob?.cancel()
        numberTimerJob?.cancel()
        _state.value = NumberMemoryState()
        generateNewNumber()
        startGameTimer()
        startNumberTimer()
    }

    fun submitAnswer() {
        val currentState = _state.value
        if (currentState.gameState == GameState.Lost) return

        if (currentState.userInput == currentState.currentNumber) {
            // Правильный ответ
            _state.value = currentState.copy(
                score = currentState.score + 1,
                userInput = "",
                showNumber = true
            )
            generateNewNumber()
            startNumberTimer()
        } else {
            // Неправильный ответ
            _state.value = currentState.copy(
                userInput = "",
                showNumber = true
            )
            generateNewNumber()
            startNumberTimer()
        }
    }

    fun updateUserInput(input: String) {
        _state.value = _state.value.copy(userInput = input)
    }

    fun pauseGame() {
        gameTimerJob?.cancel()
        numberTimerJob?.cancel()
        previousTimeLeft = _state.value.timeLeftInMillis
        _state.value = _state.value.copy(gameState = GameState.Paused)
    }

    fun resumeGame() {
        _state.value = _state.value.copy(
            gameState = GameState.Playing,
            timeLeftInMillis = previousTimeLeft
        )
        startGameTimer()
        if (_state.value.showNumber) {
            startNumberTimer()
        }
    }

    private fun generateNewNumber() {
        val length = _state.value.score + 3 // Начинаем с 3 цифр и увеличиваем с каждым правильным ответом
        val number = buildString {
            repeat(length) {
                append(Random.nextInt(10))
            }
        }
        _state.value = _state.value.copy(currentNumber = number)
    }

    private fun startGameTimer() {
        gameTimerJob?.cancel()
        gameTimerJob = viewModelScope.launch {
            while (_state.value.timeLeftInMillis > 0) {
                delay(100) // Обновляем каждые 100мс
                _state.value = _state.value.copy(
                    timeLeftInMillis = (_state.value.timeLeftInMillis - 100).coerceAtLeast(0)
                )
            }
            // Когда время вышло, игра заканчивается
            _state.value = _state.value.copy(gameState = GameState.Lost)
        }
    }

    private fun startNumberTimer() {
        numberTimerJob?.cancel()
        numberTimerJob = viewModelScope.launch {
            _state.value = _state.value.copy(showNumber = true)
            delay(_state.value.timeToMemorize) // Показываем число 2 секунды
            _state.value = _state.value.copy(showNumber = false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        gameTimerJob?.cancel()
        numberTimerJob?.cancel()
    }
}
