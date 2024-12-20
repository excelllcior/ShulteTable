package com.example.shultetable.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ExercisesScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Выберите игру",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 32.dp)
        )

        GameCard(
            title = "Таблица Шульте",
            description = "Найдите числа по порядку от 1 до 25. Тренирует периферическое зрение и внимательность.",
            onClick = { navController.navigate("shulteTable") }
        )

        GameCard(
            title = "Запомни число",
            description = "Запоминайте и воспроизводите числа возрастающей длины. Тренирует кратковременную память.",
            onClick = { navController.navigate("numberMemory") }
        )

        GameCard(
            title = "Цветовые пары",
            description = "Найдите все парные карточки одинакового цвета. Тренирует память и концентрацию.",
            onClick = { navController.navigate("colorPairs") }
        )

        GameCard(
            title = "Математика",
            description = "Реши простейшие математические задачи. Тренирует логическое мышление.",
            onClick = { navController.navigate("mathGame") }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameCard(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
