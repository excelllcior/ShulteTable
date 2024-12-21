package com.example.shultetable.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shultetable.R
import com.example.shultetable.ui.theme.CharlestonGreen
import com.example.shultetable.ui.theme.CharlestonGreen30
import com.example.shultetable.ui.theme.CharlestonGreen60
import com.example.shultetable.ui.theme.CharlestonGreenTransparent
import com.example.shultetable.ui.theme.Crayola
import com.example.shultetable.ui.theme.Cyan
import com.example.shultetable.ui.theme.Mindaro
import com.example.shultetable.ui.theme.Purple
import com.example.shultetable.ui.theme.jostFamily

@Composable
fun ExercisesScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Упражнения",
            fontSize = 32.sp,
            fontFamily = jostFamily,
            fontWeight = FontWeight.Medium,
            color = CharlestonGreen
        )
        Spacer(modifier = Modifier.height(32.dp))
        Section(
            title = "Мне нравится"
        ) {
            LazyRow {
                item {
                    GameCard(
                        title = "Таблица Шульте",
                        category = "Внимательность",
                        color = Mindaro,
                        bestScore = 1280,
                        onClick = { navController.navigate("shulteTable") }
                    )
                    Spacer(Modifier.width(16.dp))
                }
                item {
                    GameCard(
                        title = "Запомни число",
                        category = "Память",
                        color = Purple,
                        bestScore = 2080,
                        onClick = { navController.navigate("numberMemory") }
                    )
                    Spacer(Modifier.width(16.dp))
                }
                item {
                    GameCard(
                        title = "Цветовые пары",
                        category = "Концентрация",
                        color = Crayola,
                        bestScore = 2640,
                        onClick = { navController.navigate("colorPairs") }
                    )
                    Spacer(Modifier.width(16.dp))
                }
                item {
                    GameCard(
                        title = "Математика",
                        category = "Логика",
                        color = Cyan,
                        bestScore = 3700,
                        onClick = { navController.navigate("mathGame") }
                    )
                    Spacer(Modifier.width(16.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Section(
            title = "Мне нравится"
        ) {
            LazyRow {
                item {
                    GameCard(
                        title = "Математика",
                        category = "Логика",
                        color = Cyan,
                        bestScore = 3700,
                        onClick = { navController.navigate("mathGame") }
                    )
                    Spacer(Modifier.width(16.dp))
                }
                item {
                    GameCard(
                        title = "Цветовые пары",
                        category = "Концентрация",
                        color = Crayola,
                        bestScore = 2640,
                        onClick = { navController.navigate("colorPairs") }
                    )
                    Spacer(Modifier.width(16.dp))
                }
                item {
                    GameCard(
                        title = "Таблица Шульте",
                        category = "Внимательность",
                        color = Mindaro,
                        bestScore = 1280,
                        onClick = { navController.navigate("shulteTable") }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameCard(
    title: String,
    category: String,
    color: Color = Mindaro,
    bestScore: Int,
    onClick: () -> Unit
) {
    Card(onClick = onClick) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(144.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color)
            .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Text(
                        text = title,
                        fontSize = 24.sp,
                        fontFamily = jostFamily,
                        fontWeight = FontWeight.Normal,
                        color = CharlestonGreen
                    )
                    Text(
                        text = category,
                        fontSize = 16.sp,
                        fontFamily = jostFamily,
                        fontWeight = FontWeight.Normal,
                        color = CharlestonGreenTransparent
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        Icons.Filled.Favorite,
                        modifier = Modifier.size(32.dp),
                        contentDescription = "Like Button",
                        tint = CharlestonGreen
                    )
                }
            }
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Максимальный счёт — $bestScore",
                        fontSize = 16.sp,
                        fontFamily = jostFamily,
                        fontWeight = FontWeight.Normal,
                        color = CharlestonGreen
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        painter = painterResource(R.drawable.star),
                        modifier = Modifier.size(14.dp),
                        contentDescription = "Like Button",
                        tint = CharlestonGreen
                    )
                }
            }
        }
    }
}