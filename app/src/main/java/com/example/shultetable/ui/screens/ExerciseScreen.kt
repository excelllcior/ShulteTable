package com.example.shultetable.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shultetable.R
import com.example.shultetable.model.ExerciseCardData

@Preview(showBackground = true)
@Composable
fun ExerciseScreen(navController: NavController = rememberNavController()) {
    // Начальный массив элементов
    val cardTypeList = listOf(
        ExerciseCardData(0, "Внимание", 1140),
        ExerciseCardData(1, "Внимание", 200),
        ExerciseCardData(2, "Внимание", 300),
        ExerciseCardData(3, "Внимание", 400),
        ExerciseCardData(4, "Внимание", 500)
    )

    val cardList = listOf(
        ExerciseCardData(0, "Внимание", 1140),
        ExerciseCardData(1, "Внимание", 200),
        ExerciseCardData(2, "Внимание", 300),
        ExerciseCardData(3, "Внимание", 400),
        ExerciseCardData(4, "Внимание", 500)
    )

    val exerciseTypeState = rememberLazyListState()
    val exerciseState = rememberLazyListState()

    LaunchedEffect(Unit) {
        exerciseTypeState.scrollToItem(2)
        exerciseState.scrollToItem(2)
    }

    Column(Modifier.fillMaxSize().padding(top = 16.dp)) {
        LazyRow(state = exerciseTypeState,
            contentPadding = PaddingValues(horizontal =  40.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(cardTypeList) { index, item ->
                ExerciseTypeCard(item)
            }
        }
        Spacer(Modifier.height(16.dp))
        LazyRow(state = exerciseState,
            contentPadding = PaddingValues(horizontal =  40.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(cardList) { index, item ->
                ExerciseCard(Modifier.weight(1f), item)
            }
        }
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Начать упражение")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExerciseCard(modifier: Modifier = Modifier,item: ExerciseCardData = ExerciseCardData(0,"Внимание",1400)){
    ElevatedCard(
        modifier = Modifier.width(300.dp).height(500.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Память")
                    Text("Таблица Шульте")
                }
                Icon(
                    painter = painterResource(R.drawable.star),
                    contentDescription = "Звезда"
                )
            }
            Spacer(Modifier.height(20.dp))
            Text("Таблица Шульте — это поле, поделенное на 25 одинаковых квадратов " +
                    "(5х5). В клетки вписаны вразброс числа от 1 до 25 или буквы. " +
                    "Метод разработал немецкий психиатр Вальтер Шульте для тренировки вни" +
                    "мания, памяти и улучшения концентрации.")
            Spacer(Modifier.height(20.dp))
//            Image(
//                painter = painterResource(R.drawable.strategy),
//                contentDescription = "Стротег",
//                modifier = Modifier.size(180.dp),
//            )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun ExerciseTypeCard(item: ExerciseCardData = ExerciseCardData(0, "Внимание", 1140)) {
    ElevatedCard(
        modifier = Modifier.width(300.dp).height(116.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(item.title)
                Text("Новичок")
            }
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("До следующего ранга — ")
                    Text(item.value.toString())
                    Icon(
                        painter = painterResource(R.drawable.star),
                        contentDescription = "Звезды",
                    )
                }
            }
        }
    }
}