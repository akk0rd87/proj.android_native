package com.jcross.prototype.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jcross.prototype.ui.screens.*

/**
 * Маршруты навигации
 */
sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Group : Screen("group/{groupId}") {
        fun createRoute(groupId: Int) = "group/$groupId"
    }
    object Folder : Screen("folder/{groupId}/{folderId}") {
        fun createRoute(groupId: Int, folderId: Int) = "folder/$groupId/$folderId"
    }
    object Game : Screen("game/{groupId}/{folderId}/{puzzleId}") {
        fun createRoute(groupId: Int, folderId: Int, puzzleId: Int) =
            "game/$groupId/$folderId/$puzzleId"
    }
    object Options : Screen("options")
    object Themes : Screen("themes")
}

/**
 * Граф навигации приложения
 */
@Composable
fun AppNavigation(
    navController: NavHostController,
    onThemeSelected: (com.jcross.prototype.model.AppTheme) -> Unit = {},
    onImmersiveModeChange: (Boolean) -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        // Главный экран
        composable(Screen.Main.route) {
            MainScreen(
                onGroupSelected = { groupId ->
                    navController.navigate(Screen.Group.createRoute(groupId))
                },
                onNavigateToOptions = {
                    navController.navigate(Screen.Options.route)
                },
                onNavigateToThemes = {
                    navController.navigate(Screen.Themes.route)
                }
            )
        }

        // Экран группы (список папок)
        composable(
            route = Screen.Group.route,
            arguments = listOf(navArgument("groupId") { type = NavType.IntType })
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getInt("groupId") ?: 0

            GroupScreen(
                groupId = groupId,
                onBackClick = { navController.popBackStack() },
                onFolderSelected = { folderId ->
                    navController.navigate(Screen.Folder.createRoute(groupId, folderId))
                }
            )
        }

        // Экран папки (список головоломок)
        composable(
            route = Screen.Folder.route,
            arguments = listOf(
                navArgument("groupId") { type = NavType.IntType },
                navArgument("folderId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getInt("groupId") ?: 0
            val folderId = backStackEntry.arguments?.getInt("folderId") ?: 0

            FolderScreen(
                groupId = groupId,
                folderId = folderId,
                onBackClick = { navController.popBackStack() },
                onPuzzleSelected = { puzzleId ->
                    navController.navigate(Screen.Game.createRoute(groupId, folderId, puzzleId))
                }
            )
        }

        // Экран игры
        composable(
            route = Screen.Game.route,
            arguments = listOf(
                navArgument("groupId") { type = NavType.IntType },
                navArgument("folderId") { type = NavType.IntType },
                navArgument("puzzleId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getInt("groupId") ?: 0
            val folderId = backStackEntry.arguments?.getInt("folderId") ?: 0
            val puzzleId = backStackEntry.arguments?.getInt("puzzleId") ?: 0

            GameScreen(
                groupId = groupId,
                folderId = folderId,
                puzzleId = puzzleId,
                onBackClick = { navController.popBackStack() },
                onImmersiveModeChange = onImmersiveModeChange
            )
        }

        // Экран настроек
        composable(Screen.Options.route) {
            OptionsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        // Экран тем
        composable(Screen.Themes.route) {
            ThemesScreen(
                onBackClick = { navController.popBackStack() },
                onThemeSelected = onThemeSelected
            )
        }
    }
}
