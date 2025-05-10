package com.omooooori.feature.userdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omooooori.data.GithubApiError
import com.omooooori.data.mapper.toModel
import com.omooooori.domain.FetchUserDetailUseCase
import com.omooooori.domain.FetchUserEventsUseCase
import com.omooooori.model.GithubUserDetail
import com.omooooori.model.GithubUserEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val fetchUserDetailUseCase: FetchUserDetailUseCase,
    private val fetchUserEventsUseCase: FetchUserEventsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UserDetailUiState>(UserDetailUiState.Idle)
    val uiState: StateFlow<UserDetailUiState> = _uiState

    fun fetchUserDetail(
        username: String,
        avatarUrl: String,
    ) {
        _uiState.value = UserDetailUiState.Loading

        viewModelScope.launch {
            try {
                val userDetail = fetchUserDetailUseCase.execute(username).toModel()
                val events = fetchUserEventsUseCase.execute(username).map { it.toModel() }
                _uiState.value = UserDetailUiState.Success(
                    avatarUrl = avatarUrl,
                    userDetail = userDetail,
                    events = events
                )
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is GithubApiError -> e.message ?: "予期せぬエラーが発生しました"
                    else -> "予期せぬエラーが発生しました"
                }
                _uiState.value = UserDetailUiState.Error(message = errorMessage)
            }
        }
    }
}
