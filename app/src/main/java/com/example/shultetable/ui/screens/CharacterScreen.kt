package com.example.shultetable.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import com.example.shultetable.R
import com.example.shultetable.ui.theme.jostFamily

@Preview(showBackground = true)
@Composable
fun CharacterScreen() {
    Column(
        Modifier.fillMaxSize().padding(12.dp),
    ) {
        Text(
            "Персонаж",
            fontFamily = jostFamily,
            fontSize = 28.sp,
        )
        Image(
            painter = painterResource(R.drawable.studski),
            contentDescription = "studski",
            Modifier.size(364.dp)
        )

        HexagonWithImage(R.drawable.studski)
    }
}

@Composable
fun HexagonWithImage(imageResId: Int) {
    Box(
        modifier = Modifier.size(200.dp)
            .drawWithCache {
                val path = Path().apply {
                    // Определяем координаты вершин шестиугольника
                    moveTo(size.width / 4, 0f) // Верхняя вершина
                    lineTo(size.width*3/4, 0f)
                    lineTo(size.width, size.height / 2)
                    lineTo(size.width*3/4, size.height)// Верхняя правая вершина
                    lineTo(size.width / 4, size.height) // Нижняя вершина
                    lineTo(0f, size.height / 2) // Нижняя левая вершина
                    close() // Замыкаем путь
                }
                onDrawBehind {
                    // Рисуем границу шестиугольника
                    drawPath(path, color = Color.Black, style = Stroke(width = 4f))
                }
            }
            .fillMaxSize(),
    ) {
        // Рисуем изображение внутри шестиугольника
        Image(
            painter = painterResource(id = imageResId), // Замените на ваше изображение
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            alignment = Alignment.Center// Занимает весь размер шестиугольника
        )
    }
}