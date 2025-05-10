package com.omooooori.feature.userlist

sealed interface UserListUiState {
    data object Loading : UserListUiState

    data object Success : UserListUiState

    data class Error(val message: String) : UserListUiState
}
