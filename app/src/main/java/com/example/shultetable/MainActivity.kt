package com.example.shultetable

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.shultetable.ui.components.BottomNavigationBar
import com.example.shultetable.ui.screens.*
import com.example.shultetable.ui.theme.ShulteTableTheme
import com.example.shultetable.viewmodel.Exercise
import com.example.shultetable.viewmodel.TrainingViewModel
import com.google.gson.Gson
import java.net.URLEncoder
import java.net.URLDecoder

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShulteTableTheme {
                MainContent()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent() {
    val navController = rememberNavController()
    val trainingViewModel: TrainingViewModel = viewModel()
    val gson = Gson()

    val showBottomNav = remember {
        listOf("main", "exercises", "stats", "character", "profile")
    }

    val currentRoute = navController
        .currentBackStackEntryAsState()
        .value?.destination?.route ?: ""

    Scaffold(
        bottomBar = {
            if (currentRoute in showBottomNav) {
                BottomNavigationBar(navController)
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(navController = navController, startDestination = "main") {
                composable("main") {
                    HomeScreen(
                        navController = navController,
                        onStartTraining = {
                            trainingViewModel.startTraining()
                            when (trainingViewModel.currentExercise) {
                                Exercise.SCHULTE_TABLE -> navController.navigate("shulteTable?training=true")
                                Exercise.NUMBER_MEMORY -> navController.navigate("numberMemory?training=true")
                                Exercise.COLOR_PAIRS -> navController.navigate("colorPairs?training=true")
                                Exercise.MATH_GAME -> navController.navigate("mathGame?training=true")
                                null -> {}
                            }
                        },
                        onNavigateToTrainingPlan = {
                            navController.navigate("trainingPlan")
                        }
                    )
                }

                composable("exercises") {
                    ExercisesScreen(navController)
                }

                composable("stats") {
                    StatsScreen()
                }

                composable("character") {
                    CharacterScreen()
                }

                composable("profile") {
                    ProfileScreen(
                        onNavigateToTrainingPlan = {
                            navController.navigate("trainingPlan")
                        }
                    )
                }

                composable("trainingPlan") {
                    TrainingPlanScreen(
                        onNavigateBack = {
                            navController.popBackStack()
                        }
                    )
                }

                composable("shulteTable") {
                    ShulteTableScreen(
                        onNavigateToMain = { navController.navigate("exercises") },
                        onNavigateToResults = { title, results ->
                            val resultsJson = gson.toJson(results)
                            val encodedTitle = URLEncoder.encode(title, "UTF-8")
                            val encodedResults = URLEncoder.encode(resultsJson, "UTF-8")
                            navController.navigate("gameResults/$encodedTitle/$encodedResults") {
                                launchSingleTop = true
                                popUpTo("shulteTable") { inclusive = true }
                            }
                        }
                    )
                }

                composable(
                    route = "shulteTable?training={training}",
                    arguments = listOf(
                        navArgument("training") { 
                            type = NavType.BoolType 
                            defaultValue = false
                        }
                    )
                ) { backStackEntry ->
                    val isTraining = backStackEntry.arguments?.getBoolean("training") ?: false
                    ShulteTableScreen(
                        onNavigateToMain = { navController.navigate("exercises") },
                        onNavigateToResults = { title, results ->
                            val resultsJson = gson.toJson(results)
                            val encodedTitle = URLEncoder.encode(title, "UTF-8")
                            val encodedResults = URLEncoder.encode(resultsJson, "UTF-8")
                            if (isTraining) {
                                navController.navigate("trainingResults/$encodedTitle/$encodedResults") {
                                    launchSingleTop = true
                                    popUpTo("shulteTable") { inclusive = true }
                                }
                            } else {
                                navController.navigate("gameResults/$encodedTitle/$encodedResults") {
                                    launchSingleTop = true
                                    popUpTo("shulteTable") { inclusive = true }
                                }
                            }
                        }
                    )
                }

                composable("numberMemory") {
                    NumberMemoryScreen(
                        onNavigateToMain = { navController.navigate("exercises") },
                        onNavigateToResults = { title, results ->
                            val resultsJson = gson.toJson(results)
                            val encodedTitle = URLEncoder.encode(title, "UTF-8")
                            val encodedResults = URLEncoder.encode(resultsJson, "UTF-8")
                            navController.navigate("gameResults/$encodedTitle/$encodedResults") {
                                launchSingleTop = true
                                popUpTo("numberMemory") { inclusive = true }
                            }
                        }
                    )
                }

                composable(
                    route = "numberMemory?training={training}",
                    arguments = listOf(
                        navArgument("training") { 
                            type = NavType.BoolType 
                            defaultValue = false
                        }
                    )
                ) { backStackEntry ->
                    val isTraining = backStackEntry.arguments?.getBoolean("training") ?: false
                    NumberMemoryScreen(
                        onNavigateToMain = { navController.navigate("exercises") },
                        onNavigateToResults = { title, results ->
                            val resultsJson = gson.toJson(results)
                            val encodedTitle = URLEncoder.encode(title, "UTF-8")
                            val encodedResults = URLEncoder.encode(resultsJson, "UTF-8")
                            if (isTraining) {
                                navController.navigate("trainingResults/$encodedTitle/$encodedResults") {
                                    launchSingleTop = true
                                    popUpTo("numberMemory") { inclusive = true }
                                }
                            } else {
                                navController.navigate("gameResults/$encodedTitle/$encodedResults") {
                                    launchSingleTop = true
                                    popUpTo("numberMemory") { inclusive = true }
                                }
                            }
                        }
                    )
                }

                composable("colorPairs") {
                    ColorPairsScreen(
                        onNavigateToMain = { navController.navigate("exercises") },
                        onNavigateToResults = { title, results ->
                            val resultsJson = gson.toJson(results)
                            val encodedTitle = URLEncoder.encode(title, "UTF-8")
                            val encodedResults = URLEncoder.encode(resultsJson, "UTF-8")
                            navController.navigate("gameResults/$encodedTitle/$encodedResults") {
                                launchSingleTop = true
                                popUpTo("colorPairs") { inclusive = true }
                            }
                        }
                    )
                }

                composable(
                    route = "colorPairs?training={training}",
                    arguments = listOf(
                        navArgument("training") { 
                            type = NavType.BoolType 
                            defaultValue = false
                        }
                    )
                ) { backStackEntry ->
                    val isTraining = backStackEntry.arguments?.getBoolean("training") ?: false
                    ColorPairsScreen(
                        onNavigateToMain = { navController.navigate("exercises") },
                        onNavigateToResults = { title, results ->
                            val resultsJson = gson.toJson(results)
                            val encodedTitle = URLEncoder.encode(title, "UTF-8")
                            val encodedResults = URLEncoder.encode(resultsJson, "UTF-8")
                            if (isTraining) {
                                navController.navigate("trainingResults/$encodedTitle/$encodedResults") {
                                    launchSingleTop = true
                                    popUpTo("colorPairs") { inclusive = true }
                                }
                            } else {
                                navController.navigate("gameResults/$encodedTitle/$encodedResults") {
                                    launchSingleTop = true
                                    popUpTo("colorPairs") { inclusive = true }
                                }
                            }
                        }
                    )
                }

                composable("mathGame") {
                    MathGameScreen(
                        onNavigateToMain = { navController.navigate("exercises") },
                        onNavigateToResults = { title, results ->
                            val resultsJson = gson.toJson(results)
                            val encodedTitle = URLEncoder.encode(title, "UTF-8")
                            val encodedResults = URLEncoder.encode(resultsJson, "UTF-8")
                            navController.navigate("gameResults/$encodedTitle/$encodedResults") {
                                launchSingleTop = true
                                popUpTo("mathGame") { inclusive = true }
                            }
                        }
                    )
                }

                composable(
                    route = "mathGame?training={training}",
                    arguments = listOf(
                        navArgument("training") { 
                            type = NavType.BoolType 
                            defaultValue = false
                        }
                    )
                ) { backStackEntry ->
                    val isTraining = backStackEntry.arguments?.getBoolean("training") ?: false
                    MathGameScreen(
                        onNavigateToMain = { navController.navigate("exercises") },
                        onNavigateToResults = { title, results ->
                            val resultsJson = gson.toJson(results)
                            val encodedTitle = URLEncoder.encode(title, "UTF-8")
                            val encodedResults = URLEncoder.encode(resultsJson, "UTF-8")
                            if (isTraining) {
                                navController.navigate("trainingResults/$encodedTitle/$encodedResults") {
                                    launchSingleTop = true
                                    popUpTo("mathGame") { inclusive = true }
                                }
                            } else {
                                navController.navigate("gameResults/$encodedTitle/$encodedResults") {
                                    launchSingleTop = true
                                    popUpTo("mathGame") { inclusive = true }
                                }
                            }
                        }
                    )
                }

                composable(
                    route = "gameResults/{gameTitle}/{results}",
                    arguments = listOf(
                        navArgument("gameTitle") { type = NavType.StringType },
                        navArgument("results") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val gameTitle = URLDecoder.decode(
                        backStackEntry.arguments?.getString("gameTitle"),
                        "UTF-8"
                    )
                    val resultsJson = URLDecoder.decode(
                        backStackEntry.arguments?.getString("results"),
                        "UTF-8"
                    )
                    
                    val results = gson.fromJson<List<Pair<String, String>>>(
                        resultsJson,
                        object : com.google.gson.reflect.TypeToken<List<Pair<String, String>>>() {}.type
                    )

                    GameResultScreen(
                        gameTitle = gameTitle,
                        results = results,
                        onPlayAgain = {
                            when (gameTitle) {
                                "Таблица Шульте" -> navController.navigate("shulteTable")
                                "Запомни число" -> navController.navigate("numberMemory")
                                "Цветовые пары" -> navController.navigate("colorPairs")
                                "Математика" -> navController.navigate("mathGame")
                            }
                        },
                        onNavigateToMain = { navController.navigate("exercises") }
                    )
                }

                composable(
                    route = "trainingResults/{gameTitle}/{results}",
                    arguments = listOf(
                        navArgument("gameTitle") { type = NavType.StringType },
                        navArgument("results") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val gameTitle = URLDecoder.decode(
                        backStackEntry.arguments?.getString("gameTitle"),
                        "UTF-8"
                    )
                    val resultsJson = URLDecoder.decode(
                        backStackEntry.arguments?.getString("results"),
                        "UTF-8"
                    )
                    
                    val results = gson.fromJson<List<Pair<String, String>>>(
                        resultsJson,
                        object : com.google.gson.reflect.TypeToken<List<Pair<String, String>>>() {}.type
                    )

                    TrainingResultScreen(
                        gameTitle = gameTitle,
                        results = results,
                        onContinue = {
                            if (trainingViewModel.isLastExercise) {
                                navController.navigate("trainingComplete")
                            } else {
                                trainingViewModel.moveToNextExercise()
                                when (trainingViewModel.currentExercise) {
                                    Exercise.SCHULTE_TABLE -> navController.navigate("shulteTable?training=true")
                                    Exercise.NUMBER_MEMORY -> navController.navigate("numberMemory?training=true")
                                    Exercise.COLOR_PAIRS -> navController.navigate("colorPairs?training=true")
                                    Exercise.MATH_GAME -> navController.navigate("mathGame?training=true")
                                    null -> {}
                                }
                            }
                        }
                    )
                }

                composable("trainingComplete") {
                    TrainingCompleteScreen(
                        onFinishTraining = {
                            trainingViewModel.finishTraining()
                            navController.navigate("main") {
                                popUpTo("main") { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}
