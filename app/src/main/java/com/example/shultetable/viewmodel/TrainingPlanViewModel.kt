package com.example.shultetable.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.shultetable.model.DifficultyLevel
import com.example.shultetable.service.NotificationService
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
class TrainingPlanViewModel(application: Application) : AndroidViewModel(application) {
    private val notificationService = NotificationService(application)
    private val prefs: SharedPreferences = application.getSharedPreferences(
        "training_settings",
        Context.MODE_PRIVATE
    )

    var pushNotificationsEnabled by mutableStateOf(
        prefs.getBoolean(PREF_NOTIFICATIONS_ENABLED, false)
    )
        private set
    
    var notificationTime by mutableStateOf(
        LocalTime.of(
            prefs.getInt(PREF_NOTIFICATION_HOUR, 10),
            prefs.getInt(PREF_NOTIFICATION_MINUTE, 0)
        )
    )
        private set

    private var _difficultyLevel = mutableStateOf(
        DifficultyLevel.values()[prefs.getInt(PREF_DIFFICULTY_LEVEL, 0)]
    )
    val difficultyLevel: DifficultyLevel get() = _difficultyLevel.value

    private var _exerciseCount = mutableStateOf(
        prefs.getInt(PREF_EXERCISE_COUNT, 3)
    )
    val exerciseCount: Int get() = _exerciseCount.value

    var isAttentionEnabled by mutableStateOf(
        prefs.getBoolean(PREF_ATTENTION_ENABLED, true)
    )
        private set

    var isMemoryEnabled by mutableStateOf(
        prefs.getBoolean(PREF_MEMORY_ENABLED, true)
    )
        private set

    var isConcentrationEnabled by mutableStateOf(
        prefs.getBoolean(PREF_CONCENTRATION_ENABLED, true)
    )
        private set

    var isLogicEnabled by mutableStateOf(
        prefs.getBoolean(PREF_LOGIC_ENABLED, true)
    )
        private set

    private val _selectedExercises = mutableStateOf(
        prefs.getStringSet(PREF_SELECTED_EXERCISES, setOf(
            "Таблица Шульте",
            "Запомни число",
            "Цветовые пары",
            "Математика"
        )) ?: setOf()
    )
    val selectedExercises: Set<String> get() = _selectedExercises.value

    fun setDifficultyLevel(level: DifficultyLevel) {
        _difficultyLevel.value = level
        prefs.edit().putInt(PREF_DIFFICULTY_LEVEL, level.ordinal).apply()
    }

    fun setExerciseCount(count: Int) {
        if (count in 1..5) {
            _exerciseCount.value = count
            prefs.edit().putInt(PREF_EXERCISE_COUNT, count).apply()
        }
    }
    
    @RequiresApi(Build.VERSION_CODES.O)
    fun togglePushNotifications() {
        pushNotificationsEnabled = !pushNotificationsEnabled
        prefs.edit().putBoolean(PREF_NOTIFICATIONS_ENABLED, pushNotificationsEnabled).apply()
        
        if (pushNotificationsEnabled) {
            notificationService.scheduleNotification(notificationTime)
        } else {
            notificationService.cancelNotification()
        }
    }
    
    @RequiresApi(Build.VERSION_CODES.O)
    fun setNotificationTime(hour: Int, minute: Int) {
        notificationTime = LocalTime.of(hour, minute)
        prefs.edit()
            .putInt(PREF_NOTIFICATION_HOUR, hour)
            .putInt(PREF_NOTIFICATION_MINUTE, minute)
            .apply()
            
        if (pushNotificationsEnabled) {
            notificationService.scheduleNotification(notificationTime)
        }
    }

    fun toggleAttention() {
        isAttentionEnabled = !isAttentionEnabled
        prefs.edit().putBoolean(PREF_ATTENTION_ENABLED, isAttentionEnabled).apply()

        val newExercises = _selectedExercises.value.toMutableSet()
        if (!isAttentionEnabled) {
            newExercises.remove("Таблица Шульте")
        } else {
            newExercises.add("Таблица Шульте")
        }
        _selectedExercises.value = newExercises
        prefs.edit().putStringSet(PREF_SELECTED_EXERCISES, newExercises).apply()
    }

    fun toggleMemory() {
        isMemoryEnabled = !isMemoryEnabled
        prefs.edit().putBoolean(PREF_MEMORY_ENABLED, isMemoryEnabled).apply()

        val newExercises = _selectedExercises.value.toMutableSet()
        if (!isMemoryEnabled) {
            newExercises.remove("Запомни число")
        } else {
            newExercises.add("Запомни число")
        }
        _selectedExercises.value = newExercises
        prefs.edit().putStringSet(PREF_SELECTED_EXERCISES, newExercises).apply()
    }

    fun toggleConcentration() {
        isConcentrationEnabled = !isConcentrationEnabled
        prefs.edit().putBoolean(PREF_CONCENTRATION_ENABLED, isConcentrationEnabled).apply()

        val newExercises = _selectedExercises.value.toMutableSet()
        if (!isConcentrationEnabled) {
            newExercises.remove("Цветовые пары")
        } else {
            newExercises.add("Цветовые пары")
        }
        _selectedExercises.value = newExercises
        prefs.edit().putStringSet(PREF_SELECTED_EXERCISES, newExercises).apply()
    }

    fun toggleLogic() {
        isLogicEnabled = !isLogicEnabled
        prefs.edit().putBoolean(PREF_LOGIC_ENABLED, isLogicEnabled).apply()

        val newExercises = _selectedExercises.value.toMutableSet()
        if (!isLogicEnabled) {
            newExercises.remove("Математика")
        } else {
            newExercises.add("Математика")
        }
        _selectedExercises.value = newExercises
        prefs.edit().putStringSet(PREF_SELECTED_EXERCISES, newExercises).apply()
    }

    companion object {
        private const val PREF_NOTIFICATIONS_ENABLED = "notifications_enabled"
        private const val PREF_NOTIFICATION_HOUR = "notification_hour"
        private const val PREF_NOTIFICATION_MINUTE = "notification_minute"
        private const val PREF_DIFFICULTY_LEVEL = "difficulty_level"
        private const val PREF_EXERCISE_COUNT = "exercise_count"
        private const val PREF_ATTENTION_ENABLED = "attention_enabled"
        private const val PREF_MEMORY_ENABLED = "memory_enabled"
        private const val PREF_CONCENTRATION_ENABLED = "concentration_enabled"
        private const val PREF_LOGIC_ENABLED = "logic_enabled"
        private const val PREF_SELECTED_EXERCISES = "selected_exercises"
    }
}
