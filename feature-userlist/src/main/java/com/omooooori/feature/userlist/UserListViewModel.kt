package com.omooooori.feature.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.omooooori.domain.FetchUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserListViewModel(
    private val fetchUsersUseCase: FetchUsersUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UserListUiState>(UserListUiState.Loading)
    val uiState: StateFlow<UserListUiState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.value =
                UserListUiState.Success(
                    query = "",
                    users =
                        Pager(
                            config = PagingConfig(pageSize = 20),
                            pagingSourceFactory = {
                                GithubUserPagingSource(fetchUsersUseCase)
                            },
                        ).flow.cachedIn(viewModelScope),
                )
        }
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
