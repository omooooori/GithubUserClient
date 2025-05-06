package com.omooooori.feature.userlist

import com.omooooori.model.GithubUser

sealed interface UserListUiState {
    data object Loading : UserListUiState

    data class Success(
        val query: String,
        val users: List<GithubUser>,
    ) : UserListUiState

    data class Error(val message: String) : UserListUiState
}
