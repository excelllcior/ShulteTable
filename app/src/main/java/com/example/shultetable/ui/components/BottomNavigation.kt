package com.example.shultetable.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("main", Icons.Default.Home, "Главная")
    object Exercises : BottomNavItem("exercises", Icons.Default.FitnessCenter, "Игры")
    object Stats : BottomNavItem("stats", Icons.Default.BarChart, "Статистика")
    object Character : BottomNavItem("character", Icons.Default.Person, "Персонаж")
    object Profile : BottomNavItem("profile", Icons.Default.AccountCircle, "Профиль")
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Exercises,
        BottomNavItem.Stats,
        BottomNavItem.Character,
        BottomNavItem.Profile
    )

    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { 
                    Text(
                        text = item.label,
                        fontSize = 10.sp
                    ) 
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
