package com.waarstreamt.het.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.waarstreamt.het.ui.screens.DetailScreen
import com.waarstreamt.het.ui.screens.MainScreen
import com.waarstreamt.het.ui.screens.SettingsScreen

object Routes {
    const val MAIN = "main"
    const val SETTINGS = "settings"
    const val DETAIL = "detail/{contentId}"
    fun detail(id: String) = "detail/$id"
}

@Composable
fun WaarStreamtNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.MAIN) {
        composable(Routes.MAIN) {
            MainScreen(
                onNavigateToDetail = { id -> navController.navigate(Routes.detail(id)) },
                onNavigateToSettings = {
                    navController.navigate(Routes.SETTINGS) { launchSingleTop = true }
                },
            )
        }
        composable(Routes.SETTINGS) {
            SettingsScreen(
                onNavigateHome = { navController.popBackStack(Routes.MAIN, inclusive = false) },
                onBack = { navController.popBackStack() },
            )
        }
        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("contentId") { type = NavType.StringType }),
        ) { backStack ->
            val contentId = backStack.arguments?.getString("contentId") ?: return@composable
            DetailScreen(
                contentId = contentId,
                onBack = { navController.popBackStack() },
                onNavigateHome = { navController.popBackStack(Routes.MAIN, inclusive = false) },
                onNavigateToSettings = {
                    navController.navigate(Routes.SETTINGS) { launchSingleTop = true }
                },
            )
        }
    }
}
