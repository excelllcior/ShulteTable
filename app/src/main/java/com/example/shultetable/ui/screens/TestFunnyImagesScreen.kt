package com.example.shultetable.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shultetable.R

@Preview(showBackground = true)
@Composable
fun TestFunnyImagesScreen(){
    val exerciseTypeState = rememberLazyListState()
    val images:List<Int> = listOf(R.drawable.home,R.drawable.star,R.drawable.clock,R.drawable.cowbell)
    Column {
        LazyRow(
            state = exerciseTypeState,
            contentPadding = PaddingValues(horizontal = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(images){item->
                FunnyImage(item = item)
            }
        }
    }
}

@Composable
fun FunnyImage(item:Int){
    Image(
        painter = painterResource(item),
        contentDescription = "Картинка"
    )
}