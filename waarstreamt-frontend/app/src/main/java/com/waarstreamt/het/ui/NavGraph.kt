package com.waarstreamt.het.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.waarstreamt.het.ui.screens.DetailScreen
import com.waarstreamt.het.ui.screens.HomeScreen

object Routes {
    const val HOME = "home"
    const val DETAIL = "detail/{contentId}"
    fun detail(id: String) = "detail/$id"
}

@Composable
fun WaarStreamtNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(
                onResultClick = { id ->
                    navController.navigate(Routes.detail(id))
                },
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
            )
        }
    }
}
