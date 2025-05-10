package com.omooooori.githubuserclient.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.omooooori.feature.userdetail.UserDetailScreen
import com.omooooori.feature.userdetail.UserDetailViewModel
import com.omooooori.feature.userlist.UserListScreen
import com.omooooori.githubuserclient.layout.TwoPaneLayout
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController(),
) {
    val isExpanded = windowSizeClass.widthSizeClass >= WindowWidthSizeClass.Medium

    if (isExpanded) {
        TwoPaneLayout()
    } else {
        NavHost(
            navController = navController,
            startDestination = Route.UserList.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) },
        ) {
            composable(Route.UserList.route) {
                UserListScreen(
                    onUserClick = { userId, avatarUrl ->
                        navController.navigate(
                            Route.UserDetail.createRoute(
                                userId,
                                avatarUrl,
                            ),
                        )
                    },
                )
            }

            composable(Route.UserDetail.route) { backStackEntry ->
                val username = backStackEntry.arguments?.getString("username") ?: return@composable
                val avatarUrl = backStackEntry.arguments?.getString("avatarUrl") ?: return@composable
                val userDetailViewModel: UserDetailViewModel = koinViewModel()
                val uiState by userDetailViewModel.uiState.collectAsState()
                LaunchedEffect(Unit) {
                    userDetailViewModel.fetchUserDetail(username, avatarUrl)
                }
                UserDetailScreen(uiState = uiState)
            }
        }
    }
}
