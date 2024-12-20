package com.example.shultetable.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shultetable.model.GameState
import com.example.shultetable.viewmodel.NumberMemoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberMemoryScreen(
    onNavigateToMain: () -> Unit,
    onNavigateToResults: (String, List<Pair<String, String>>) -> Unit,
    viewModel: NumberMemoryViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val bottomSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    
    LaunchedEffect(key1 = Unit) {
        viewModel.startNewGame()
    }

    LaunchedEffect(key1 = state.gameState) {
        if (state.gameState == GameState.Won || state.gameState == GameState.Lost) {
            val correctAnswers = state.score
            val lastWordLength = state.currentNumber.length
            val score = correctAnswers * 500 + lastWordLength * 250
            
            onNavigateToResults(
                "Запомни число",
                listOf(
                    "Очки" to score.toString()
                )
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            IconButton(
                onClick = { 
                    viewModel.pauseGame()
                    showBottomSheet = true
                },
                modifier = Modifier.align(Alignment.Start)
            ) {
                Icon(
                    Icons.Outlined.Pause,
                    contentDescription = "Пауза",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            Text(
                text = "Время: ${state.timeLeftInMillis / 1000} сек",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurface
            )

            LinearProgressIndicator(
                progress = state.timeLeftInMillis.toFloat() / state.totalGameTime,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Очки: ${state.score}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (state.showNumber) {
                Text(
                    text = state.currentNumber,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.onSurface
                )
            } else {
                OutlinedTextField(
                    value = state.userInput,
                    onValueChange = { viewModel.updateUserInput(it) },
                    label = { Text("Введите число") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.submitAnswer() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                ) {
                    Text("Проверить")
                }
            }
        }

        if (state.gameState == GameState.Paused && showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { 
                    showBottomSheet = false
                    viewModel.resumeGame()
                },
                sheetState = bottomSheetState,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = {
                            showBottomSheet = false
                            viewModel.startNewGame()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Начать заново")
                    }

                    Button(
                        onClick = {
                            showBottomSheet = false
                            onNavigateToMain()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Меню")
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
