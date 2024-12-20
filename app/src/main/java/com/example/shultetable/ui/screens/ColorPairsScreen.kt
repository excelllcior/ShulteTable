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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shultetable.model.ColorCard
import com.example.shultetable.model.GameState
import com.example.shultetable.viewmodel.ColorPairsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPairsScreen(
    onNavigateToMain: () -> Unit,
    onNavigateToResults: (String, List<Pair<String, String>>) -> Unit,
    viewModel: ColorPairsViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val bottomSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    
    LaunchedEffect(key1 = Unit) {
        viewModel.startNewGame()
    }

    LaunchedEffect(key1 = state.gameState) {
        if (state.gameState == GameState.Lost || state.gameState == GameState.Won) {
            val foundPairs = state.score
            val remainingTime = (state.timeLeftInMillis / 1000).toInt()
            val spendingTime = 60 - remainingTime
            val moves = state.moves
            val score = foundPairs * 400 + remainingTime * 100 - moves * 100
            
            onNavigateToResults(
                "Цветовые пары",
                listOf(
                    "Очки" to score.toString(),
                    "Найдено пар" to foundPairs.toString(),
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
                progress = state.timeLeftInMillis.toFloat() / 60000,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Найдено пар: ${state.score}/8",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Ходов: ${state.moves}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            ColorGrid(
                cards = state.cards,
                onCardClick = { viewModel.onCardClick(it) }
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
fun ColorGrid(
    cards: List<ColorCard>,
    onCardClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (row in 0..3) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (col in 0..3) {
                    val index = row * 4 + col
                    if (index < cards.size) {
                        ColorCardItem(
                            card = cards[index],
                            onClick = { onCardClick(index) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ColorCardItem(
    card: ColorCard,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (card.isRevealed || card.isMatched) card.color
                else MaterialTheme.colorScheme.surfaceVariant
            )
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(
                enabled = !card.isRevealed && !card.isMatched,
                onClick = onClick
            )
    )
}
