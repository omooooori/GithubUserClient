package com.omooooori.githubuserclient.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.UserList.route
    ) {
        composable(Screen.UserList.route) {
//            UserListScreen(
//                onUserClick = { userId ->
//                    navController.navigate(Screen.UserDetail.createRoute(userId))
//                }
//            )
        }

        composable(Screen.UserDetail.route) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: return@composable
//            UserDetailScreen(userId = userId)
        }
    }
}
