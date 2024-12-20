package com.example.shultetable.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shultetable.model.GameState
import com.example.shultetable.viewmodel.ShulteTableViewModel
import kotlin.math.sqrt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShulteTableScreen(
    onNavigateToMain: () -> Unit,
    onNavigateToResults: (String, List<Pair<String, String>>) -> Unit,
    viewModel: ShulteTableViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val bottomSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        viewModel.startNewGame()
    }

    LaunchedEffect(key1 = state.gameState) {
        if (state.gameState == GameState.Won || state.gameState == GameState.Lost) {
            val remainingTime = (state.timeLeftInMillis / 1000).toInt()
            val spendingTime = 60 - remainingTime
            val score = remainingTime * 100
            
            onNavigateToResults(
                "Таблица Шульте",
                listOf(
                    "Очки" to score.toString(),
                    "Затраченное время" to "$spendingTime сек"
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = {
                        viewModel.pauseGame()
                        showBottomSheet = true
                    }
                ) {
                    Icon(
                        Icons.Outlined.Pause,
                        contentDescription = "Пауза",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
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
                progress = state.timeLeftInMillis.toFloat() / (60 * 1000),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Text(
                text = "Найдите число: ${state.currentNumber}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            ShulteTable(
                numbers = state.numbers,
                onNumberClick = { viewModel.onNumberClick(it) }
            )
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

@Composable
fun ShulteTable(
    numbers: List<Int>,
    onNumberClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val gridSize = sqrt(numbers.size.toFloat()).toInt()
    val spacing = 8.dp
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(spacing),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        for (row in 0 until gridSize) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (col in 0 until gridSize) {
                    val index = row * gridSize + col
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant,
                                RoundedCornerShape(12.dp)
                            )
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { onNumberClick(numbers[index]) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = numbers[index].toString(),
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
