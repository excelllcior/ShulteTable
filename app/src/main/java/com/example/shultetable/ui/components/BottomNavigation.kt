package com.example.shultetable.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.shultetable.R


sealed class BottomNavItem(val route: String, val icon: Int, val label: String) {
    data object Home : BottomNavItem("main", R.drawable.home, "Главная")
    data object Exercises : BottomNavItem("exercises", R.drawable.graduation_cap, "Упражнения")
    data object Stats : BottomNavItem("stats", R.drawable.bar_chart_square, "Статистика")
    data object Character : BottomNavItem("character", R.drawable.gamepad, "Персонаж")
    object Profile : BottomNavItem("profile", R.drawable.user_profile, "Профиль")
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
                icon = { Icon(painter = painterResource(item.icon), contentDescription = item.label) },
                label = { 
                    Text(
                        text = item.label,
                        fontSize = 12.sp
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
