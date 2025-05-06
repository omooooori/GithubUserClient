package com.omooooori.githubuserclient.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.omooooori.feature_userdetail.UserDetailScreen
import com.omooooori.feature_userdetail.UserDetailViewModel
import com.omooooori.feature_userlist.UserListScreen
import com.omooooori.feature_userlist.UserListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.UserList.route
    ) {
        composable(Screen.UserList.route) {
            val userListViewModel: UserListViewModel = koinViewModel()
            val state by userListViewModel.uiState.collectAsState()
            UserListScreen(
                uiState = state,
                onUserClick = { userId, avatarUrl ->
                    navController.navigate(
                        Screen.UserDetail.createRoute(
                            userId,
                            avatarUrl
                        )
                    )
                }
            )
        }

        composable(Screen.UserDetail.route) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: return@composable
            val avatarUrl = backStackEntry.arguments?.getString("avatarUrl") ?: return@composable
            val userDetailViewModel: UserDetailViewModel = koinViewModel()
            val uiState by userDetailViewModel.uiState.collectAsState()
            LaunchedEffect(Unit) {
                userDetailViewModel.load(username, avatarUrl)
            }
            UserDetailScreen(uiState = uiState)
        }
    }
}
