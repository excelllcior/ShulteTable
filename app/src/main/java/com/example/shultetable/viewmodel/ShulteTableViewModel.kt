package com.example.shultetable.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shultetable.model.GameState
import com.example.shultetable.model.ShulteTableState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class ShulteTableViewModel : ViewModel() {
    private val _state = MutableStateFlow(ShulteTableState())
    val state: StateFlow<ShulteTableState> = _state

    private var timerJob: Job? = null

    fun startNewGame() {
        timerJob?.cancel()
        val numbers = (1..25).shuffled()
        _state.value = ShulteTableState(
            numbers = numbers,
            currentNumber = 1,
            timeLeftInMillis = 60 * 1000,
            gameState = GameState.Playing
        )
        startTimer()
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (_state.value.timeLeftInMillis > 0 && _state.value.gameState == GameState.Playing) {
                delay(100)
                _state.update { currentState ->
                    currentState.copy(
                        timeLeftInMillis = (currentState.timeLeftInMillis - 100).coerceAtLeast(0)
                    )
                }
                if (_state.value.timeLeftInMillis == 0L) {
                    _state.update { it.copy(gameState = GameState.Lost) }
                }
            }
        }
    }

    fun onNumberClick(number: Int) {
        if (_state.value.gameState != GameState.Playing) return

        if (number == _state.value.currentNumber) {
            if (number == 25) {
                _state.update { it.copy(gameState = GameState.Won) }
                timerJob?.cancel()
            } else {
                _state.update { it.copy(currentNumber = it.currentNumber + 1) }
            }
        }
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
