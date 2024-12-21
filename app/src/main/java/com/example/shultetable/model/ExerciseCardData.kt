package com.example.shultetable.model

import android.icu.text.CaseMap.Title
import androidx.compose.ui.unit.Dp

data class ExerciseCardData(
    val id:Int,
    val title:String,
    val value:Int,
    val rate: Dp,
)
