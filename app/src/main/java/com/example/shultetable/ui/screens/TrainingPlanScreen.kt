package com.example.shultetable.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shultetable.model.DifficultyLevel
import com.example.shultetable.model.ExerciseCategory
import com.example.shultetable.viewmodel.TrainingPlanViewModel
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingPlanScreen(
    onNavigateBack: () -> Unit,
    viewModel: TrainingPlanViewModel = viewModel()
) {
    var showTimePicker by remember { mutableStateOf(false) }
    var showDifficultyPicker by remember { mutableStateOf(false) }
    var showExerciseCountPicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onNavigateBack
            ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            
            Text(
                text = "План тренировки",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Уведомления",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Push-уведомления
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Push-уведомления",
                style = MaterialTheme.typography.bodyLarge
            )
            Switch(
                checked = viewModel.pushNotificationsEnabled,
                onCheckedChange = { viewModel.togglePushNotifications() }
            )
        }

        // Время уведомления
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showTimePicker = true }
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Время уведомления",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = viewModel.notificationTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (showTimePicker) {
            TimePickerDialog(
                onDismiss = { showTimePicker = false },
                onConfirm = { hour, minute ->
                    viewModel.setNotificationTime(hour, minute)
                    showTimePicker = false
                },
                initialHour = viewModel.notificationTime.hour,
                initialMinute = viewModel.notificationTime.minute
            )
        }

        Text(
            text = "Сложность",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
        )

        // Уровень сложности
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDifficultyPicker = true }
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Уровень",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = viewModel.difficultyLevel.title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Количество упражнений
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showExerciseCountPicker = true }
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Количество упражнений",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = viewModel.exerciseCount.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            text = "Тип упражнений",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
        )

        // Внимание
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Внимание",
                fontSize = 16.sp
            )
            Switch(
                checked = viewModel.isAttentionEnabled,
                onCheckedChange = { viewModel.toggleAttention() }
            )
        }

        // Память
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Память",
                fontSize = 16.sp
            )
            Switch(
                checked = viewModel.isMemoryEnabled,
                onCheckedChange = { viewModel.toggleMemory() }
            )
        }

        // Концентрация
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Концентрация",
                fontSize = 16.sp
            )
            Switch(
                checked = viewModel.isConcentrationEnabled,
                onCheckedChange = { viewModel.toggleConcentration() }
            )
        }

        // Логика
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Логика",
                fontSize = 16.sp
            )
            Switch(
                checked = viewModel.isLogicEnabled,
                onCheckedChange = { viewModel.toggleLogic() }
            )
        }
    }

    if (showDifficultyPicker) {
        ModalBottomSheet(
            onDismissRequest = { showDifficultyPicker = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Выберите уровень",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                DifficultyLevel.values().forEach { level ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.setDifficultyLevel(level)
                                showDifficultyPicker = false
                            }
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = level.title,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        if (level == viewModel.difficultyLevel) {
                            Icon(
                                Icons.Filled.Check,
                                contentDescription = "Выбрано",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }

    if (showExerciseCountPicker) {
        ModalBottomSheet(
            onDismissRequest = { showExerciseCountPicker = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Выберите количество упражнений",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                (1..4).forEach { count ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.setExerciseCount(count)
                                showExerciseCountPicker = false
                            }
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = count.toString(),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        if (count == viewModel.exerciseCount) {
                            Icon(
                                Icons.Filled.Check,
                                contentDescription = "Выбрано",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: (Int, Int) -> Unit,
    initialHour: Int,
    initialMinute: Int
) {
    var selectedHour by remember { mutableStateOf(initialHour) }
    var selectedMinute by remember { mutableStateOf(initialMinute) }
    
    val hoursList = (0..23).toList()
    val minutesList = (0..59).toList()
    
    val hourScrollState = rememberLazyListState(
        initialFirstVisibleItemIndex = initialHour
    )
    val minuteScrollState = rememberLazyListState(
        initialFirstVisibleItemIndex = initialMinute
    )
    
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(hourScrollState.firstVisibleItemIndex) {
        selectedHour = hourScrollState.firstVisibleItemIndex % 24
    }

    LaunchedEffect(minuteScrollState.firstVisibleItemIndex) {
        selectedMinute = minuteScrollState.firstVisibleItemIndex % 60
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Выберите время") },
        text = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Часы
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Часы")
                    Box(
                        modifier = Modifier
                            .height(160.dp)
                            .fillMaxWidth()
                    ) {
                        LazyColumn(
                            state = hourScrollState,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 60.dp)
                        ) {
                            items(hoursList + hoursList + hoursList) { hour ->
                                Box(
                                    modifier = Modifier
                                        .height(40.dp)
                                        .clickable {
                                            coroutineScope.launch {
                                                hourScrollState.animateScrollToItem(hour, -60)
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "%02d".format(hour % 24),
                                        style = MaterialTheme.typography.headlineMedium,
                                        modifier = Modifier.alpha(
                                            if (hour % 24 == selectedHour) 1f else 0.5f
                                        )
                                    )
                                }
                            }
                        }
                        // Разделительные линии
                        Column(modifier = Modifier.fillMaxSize()) {
                            Spacer(modifier = Modifier.weight(1f))
                            Divider()
                            Spacer(modifier = Modifier.height(40.dp))
                            Divider()
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }

                Text(
                    text = ":",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                // Минуты
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Минуты")
                    Box(
                        modifier = Modifier
                            .height(160.dp)
                            .fillMaxWidth()
                    ) {
                        LazyColumn(
                            state = minuteScrollState,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 60.dp)
                        ) {
                            items(minutesList + minutesList + minutesList) { minute ->
                                Box(
                                    modifier = Modifier
                                        .height(40.dp)
                                        .clickable {
                                            coroutineScope.launch {
                                                minuteScrollState.animateScrollToItem(minute, -60)
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "%02d".format(minute % 60),
                                        style = MaterialTheme.typography.headlineMedium,
                                        modifier = Modifier.alpha(
                                            if (minute % 60 == selectedMinute) 1f else 0.5f
                                        )
                                    )
                                }
                            }
                        }
                        // Разделительные линии
                        Column(modifier = Modifier.fillMaxSize()) {
                            Spacer(modifier = Modifier.weight(1f))
                            Divider()
                            Spacer(modifier = Modifier.height(40.dp))
                            Divider()
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(selectedHour, selectedMinute) }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberPicker(
    value: Int,
    onValueChange: (Int) -> Unit,
    range: IntRange
) {
    OutlinedTextField(
        value = value.toString().padStart(2, '0'),
        onValueChange = { newValue ->
            newValue.toIntOrNull()?.let {
                if (it in range) {
                    onValueChange(it)
                }
            }
        },
        modifier = Modifier.width(64.dp),
        textStyle = MaterialTheme.typography.headlineMedium,
        singleLine = true
    )
}
