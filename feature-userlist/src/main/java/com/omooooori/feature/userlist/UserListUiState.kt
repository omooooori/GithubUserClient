package com.omooooori.feature.userlist

import androidx.paging.PagingData
import com.omooooori.model.GithubUser
import kotlinx.coroutines.flow.Flow

sealed interface UserListUiState {
    data object Loading : UserListUiState

    data class Success(
        val query: String = "",
        val users: Flow<PagingData<GithubUser>>
    ) : UserListUiState

    data class Error(val message: String) : UserListUiState
}
