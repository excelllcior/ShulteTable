package com.example.shultetable.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shultetable.R
import com.example.shultetable.ui.theme.CharlestonGreen
import com.example.shultetable.ui.theme.GoldenRod
import com.example.shultetable.ui.theme.White
import com.example.shultetable.ui.theme.jostFamily

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen({ })
}

@Composable
fun HomeScreen(
    onStartTraining: () -> Unit
//    onSettingsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Section(
            title = "Для вас"
        ) {
            DailyWorkout(
                onStartClick = onStartTraining,
                onSettingsClick = { },
            )
            Spacer(modifier = Modifier.height(16.dp))

        }
        Text(
            text = "Добро пожаловать!",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Text(
            text = "Тренируйте свой мозг с помощью увлекательных упражнений",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = onStartTraining,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text("Начать тренировку")
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Перейдите в раздел 'Игры', чтобы выбрать отдельное упражнение",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun Section(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontFamily = jostFamily,
            fontWeight = FontWeight.Medium,
            color = CharlestonGreen
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            content()
        }
    }
}

@Composable
fun DailyWorkout(
    onSettingsClick: () -> Unit,
    onStartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(GoldenRod)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "Ежедневная тренировка",
                fontSize = 24.sp,
                fontFamily = jostFamily,
                fontWeight = FontWeight.Medium,
                color = CharlestonGreen
            )
            IconButton(
                onClick = { onSettingsClick() },
                modifier = Modifier
                    .size(32.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.workout_settings),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "Like Button",
                    tint = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier
            .fillMaxWidth()
        ) {
            Text(
                text = "Завершено на 50%",
                fontSize = 16.sp
            )
            Spacer(Modifier.height(8.dp))
            BoxWithConstraints(modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White)
            ) {
                Box(modifier = Modifier
                    .width(maxWidth / 2)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(maxHeight / 2))
                    .background(Color.Black)
                )
            }
        }
        Spacer(Modifier.height(32.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Box(modifier = Modifier
                .wrapContentWidth()
                .height(32.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp)
            ) {
                Text(
                    "Осталось 20 ч. 40 мин.",
                    fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            IconButton(
                onClick = { onStartClick() },
                modifier = Modifier
                    .clip(CircleShape)
                    .size(32.dp)
                    .background(Color.Black),
            ) {
                Icon(
                    Icons.Filled.Face,
                    modifier = Modifier.size(24.dp),
                    contentDescription = "Like Button",
                    tint = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun CalendarCard(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .height(144.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(Color.LightGray)
        .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Text(text = "Activity", fontSize = 24.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Memory", fontSize = 16.sp)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Favorite,
                    modifier = Modifier.size(32.dp),
                    contentDescription = "Like Button",
                    tint = Color.Black
                )
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Text(text = "", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(2.dp))
                Text(text = "*", fontSize = 16.sp)
            }
        }
    }
}