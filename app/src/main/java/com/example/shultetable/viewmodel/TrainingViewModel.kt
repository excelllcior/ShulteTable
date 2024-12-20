package com.example.shultetable.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrainingViewModel(application: Application) : AndroidViewModel(application) {
    private val trainingPlanViewModel = TrainingPlanViewModel(application)
    
    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises.asStateFlow()
    
    var currentExerciseIndex by mutableStateOf(0)
        private set
        
    var isTrainingActive by mutableStateOf(false)
        private set
    
    val currentExercise: Exercise?
        get() = if (isTrainingActive && currentExerciseIndex < exercises.value.size) {
            exercises.value[currentExerciseIndex]
        } else null
    
    val isLastExercise: Boolean
        get() = currentExerciseIndex == exercises.value.size - 1
    
    init {
        updateExercisesList()
    }
    
    private fun updateExercisesList() {
        val selectedExercises = trainingPlanViewModel.selectedExercises
        val updatedExercises = Exercise.values().filter { exercise ->
            when (exercise) {
                Exercise.SCHULTE_TABLE -> trainingPlanViewModel.isAttentionEnabled
                Exercise.NUMBER_MEMORY -> trainingPlanViewModel.isMemoryEnabled
                Exercise.COLOR_PAIRS -> trainingPlanViewModel.isConcentrationEnabled
                Exercise.MATH_GAME -> trainingPlanViewModel.isLogicEnabled
            }
        }
        viewModelScope.launch {
            _exercises.emit(updatedExercises)
        }
    }
    
    fun startTraining() {
        updateExercisesList()
        if (exercises.value.isNotEmpty()) {
            isTrainingActive = true
            currentExerciseIndex = 0
        }
    }
    
    fun moveToNextExercise() {
        if (currentExerciseIndex < exercises.value.size - 1) {
            currentExerciseIndex++
        }
    }
    
    fun finishTraining() {
        isTrainingActive = false
        currentExerciseIndex = 0
    }
}

enum class Exercise(val category: String, val title: String, val route: String) {
    SCHULTE_TABLE("Внимательность", "Таблица Шульте", "shulteTable"),
    NUMBER_MEMORY("Память", "Запомни число", "numberMemory"),
    COLOR_PAIRS("Концентрация", "Цветовые пары", "colorPairs"),
    MATH_GAME("Логика", "Математика", "mathGame")
}
