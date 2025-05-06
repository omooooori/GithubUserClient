package com.omooooori.feature.userdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omooooori.data.mapper.toModel
import com.omooooori.domain.FetchUserDetailUseCase
import com.omooooori.domain.FetchUserEventsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val fetchUserDetailUseCase: FetchUserDetailUseCase,
    private val fetchUserEventsUseCase: FetchUserEventsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UserDetailUiState>(UserDetailUiState.Idle)
    val uiState: StateFlow<UserDetailUiState> = _uiState

    fun load(
        username: String,
        avatarUrl: String,
    ) {
        viewModelScope.launch {
            _uiState.value = UserDetailUiState.Loading
            try {
                val userDetailDto = fetchUserDetailUseCase.execute(username)
                val eventsDto = fetchUserEventsUseCase.execute(username)

                val userDetail = userDetailDto.toModel()
                val events = eventsDto.map { it.toModel() }

                _uiState.value =
                    UserDetailUiState.Success(
                        avatarUrl = avatarUrl,
                        userDetail = userDetail,
                        events = events,
                    )
            } catch (e: Exception) {
                _uiState.value = UserDetailUiState.Error("ユーザー詳細の取得に失敗しました")
            }
        }
    }
}
