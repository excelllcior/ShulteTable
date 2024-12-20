package com.example.shultetable.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shultetable.model.ColorCard
import com.example.shultetable.model.ColorPairsState
import com.example.shultetable.model.GameState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ColorPairsViewModel : ViewModel() {
    private val _state = MutableStateFlow(ColorPairsState())
    val state: StateFlow<ColorPairsState> = _state.asStateFlow()

    private var timerJob: Job? = null
    private var previousTimeLeft: Long = 0

    private val colors = listOf(
        Color(0xFFE57373), // Red
        Color(0xFF81C784), // Green
        Color(0xFF64B5F6), // Blue
        Color(0xFFFFB74D), // Orange
        Color(0xFF9575CD), // Purple
        Color(0xFF4DB6AC), // Teal
        Color(0xFFF06292), // Pink
        Color(0xFFFFD54F)  // Yellow
    )

    fun startNewGame() {
        timerJob?.cancel()
        val cards = (colors + colors).mapIndexed { index, color ->
            ColorCard(id = index, color = color)
        }.shuffled()
        
        _state.value = ColorPairsState(cards = cards)
        startTimer()
    }

    fun onCardClick(cardIndex: Int) {
        val currentState = _state.value
        if (currentState.gameState != GameState.Playing) return
        
        val card = currentState.cards[cardIndex]
        if (card.isMatched || card.isRevealed) return

        val updatedCards = currentState.cards.toMutableList()
        updatedCards[cardIndex] = card.copy(isRevealed = true)

        if (currentState.firstSelectedCard == null) {
            // Первая карта в паре
            _state.value = currentState.copy(
                cards = updatedCards,
                firstSelectedCard = cardIndex,
                moves = currentState.moves + 1
            )
        } else {
            // Вторая карта в паре
            val firstCard = currentState.cards[currentState.firstSelectedCard]
            
            if (firstCard.color == card.color) {
                // Найдена пара
                updatedCards[currentState.firstSelectedCard] = firstCard.copy(isMatched = true)
                updatedCards[cardIndex] = card.copy(isMatched = true, isRevealed = true)
                
                val newScore = currentState.score + 1
                val allMatched = updatedCards.all { it.isMatched }
                
                _state.value = currentState.copy(
                    cards = updatedCards,
                    firstSelectedCard = null,
                    score = newScore,
                    gameState = if (allMatched) GameState.Won else GameState.Playing
                )
            } else {
                // Карты не совпадают
                _state.value = currentState.copy(
                    cards = updatedCards,
                    firstSelectedCard = null
                )
                
                // Скрываем карты через небольшую задержку
                viewModelScope.launch {
                    delay(1000)
                    val resetCards = _state.value.cards.toMutableList()
                    resetCards[cardIndex] = card.copy(isRevealed = false)
                    resetCards[currentState.firstSelectedCard] = firstCard.copy(isRevealed = false)
                    _state.value = _state.value.copy(cards = resetCards)
                }
            }
        }
    }

    fun pauseGame() {
        timerJob?.cancel()
        previousTimeLeft = _state.value.timeLeftInMillis
        _state.value = _state.value.copy(gameState = GameState.Paused)
    }

    fun resumeGame() {
        _state.value = _state.value.copy(
            gameState = GameState.Playing,
            timeLeftInMillis = previousTimeLeft
        )
        startTimer()
    }

    private fun startTimer() {
        _state.value = _state.value.copy(gameState = GameState.Playing)
        
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_state.value.timeLeftInMillis > 0) {
                delay(100)
                _state.value = _state.value.copy(
                    timeLeftInMillis = (_state.value.timeLeftInMillis - 100).coerceAtLeast(0)
                )
            }
            if (_state.value.gameState != GameState.Won) {
                _state.value = _state.value.copy(gameState = GameState.Lost)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
