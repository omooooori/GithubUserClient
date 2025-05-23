package com.omooooori.feature.userdetail

import com.omooooori.model.GithubUserDetail
import com.omooooori.model.GithubUserEvent

sealed interface UserDetailUiState {
    object Idle : UserDetailUiState

    object Loading : UserDetailUiState

    data class Success(
        val avatarUrl: String,
        val userDetail: GithubUserDetail,
        val events: List<GithubUserEvent>,
    ) : UserDetailUiState

    data class Error(
        val message: String,
    ) : UserDetailUiState
}
