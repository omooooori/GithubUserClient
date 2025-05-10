package com.omooooori.githubuserclient.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.omooooori.feature.userdetail.UserDetailScreen
import com.omooooori.feature.userdetail.UserDetailViewModel
import com.omooooori.feature.userlist.UserListScreen
import com.omooooori.feature.userlist.UserListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TwoPaneLayout() {
    val userListViewModel: UserListViewModel = koinViewModel()
    val state by userListViewModel.uiState.collectAsState()

    val userDetailViewModel: UserDetailViewModel = koinViewModel()
    val userDetailUiState by userDetailViewModel.uiState.collectAsState()

    Row(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            UserListScreen(
                uiState = state,
                onUserClick = { username, avatarUrl ->
                    userDetailViewModel.fetchUserDetail(username, avatarUrl)
                },
            )
        }
        Box(modifier = Modifier.weight(1f)) {
            UserDetailScreen(uiState = userDetailUiState)
        }
    }
}
