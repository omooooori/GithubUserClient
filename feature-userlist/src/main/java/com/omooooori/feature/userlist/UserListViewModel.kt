package com.omooooori.feature.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omooooori.data.mapper.toModel
import com.omooooori.domain.FetchUsersUseCase
import com.omooooori.model.GithubUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserListViewModel(
    private val fetchUsersUseCase: FetchUsersUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UserListUiState>(UserListUiState.Loading)
    val uiState: StateFlow<UserListUiState> = _uiState

    private var isLoadingMore = false
    private var since: Int = 0
    private val loadedUsers = mutableListOf<GithubUser>()

    init {
        fetchUsers(initial = true)
    }

    private fun fetchUsers(initial: Boolean = false) {
        if (isLoadingMore) return
        isLoadingMore = true

        viewModelScope.launch {
            if (initial) {
                _uiState.value = UserListUiState.Loading
                loadedUsers.clear()
                since = 0
            }

            try {
                val usersDto = fetchUsersUseCase.execute()
                val users = usersDto.map { it.toModel() }

                if (users.isNotEmpty()) {
                    since = users.last().id
                    loadedUsers += users
                }

                _uiState.value = UserListUiState.Success(query = "", users = loadedUsers.toList())
            } catch (e: Exception) {
                _uiState.value = UserListUiState.Error(message = e.localizedMessage ?: "エラーが発生しました")
            } finally {
                isLoadingMore = false
            }
        }
    }

    fun loadMore() {
        fetchUsers()
    }

    fun updateQuery(query: String) {
        val current = _uiState.value
        if (current is UserListUiState.Success) {
            _uiState.update {
                current.copy(query = query)
            }
        }
    }
}
