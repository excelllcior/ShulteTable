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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shultetable.model.GameState
import com.example.shultetable.viewmodel.MathGameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MathGameScreen(
    onNavigateToResults: (String, List<Pair<String, String>>) -> Unit,
    onNavigateToMain: () -> Unit = {},
    viewModel: MathGameViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val bottomSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = state.gameState) {
        if (state.gameState == GameState.Won || state.gameState == GameState.Lost) {
            val correctAnswers = state.score
            val wrongAnswers = state.wrongAnswers
            val score = correctAnswers * 250 - wrongAnswers * 125
            
            onNavigateToResults(
                "Математика",
                listOf(
                    "Очки" to score.toString()
                )
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.startNewGame()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
                progress = state.timeLeftInMillis.toFloat() / (60 * 1000),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            state.currentProblem?.let { problem ->
                Text(
                    text = problem.expression,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(32.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    problem.options.chunked(2).forEach { rowOptions ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            rowOptions.forEach { option ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(2f)
                                        .background(
                                            MaterialTheme.colorScheme.surfaceVariant,
                                            RoundedCornerShape(12.dp)
                                        )
                                        .border(
                                            width = 2.dp,
                                            color = MaterialTheme.colorScheme.outline,
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .clickable { viewModel.checkAnswer(option) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = option.toString(),
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

            Text(
                text = "Решено задач: ${state.score}",
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 32.dp),
                color = MaterialTheme.colorScheme.onSurface
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
