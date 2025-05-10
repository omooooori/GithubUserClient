package com.omooooori.feature.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.omooooori.domain.FetchUsersUseCase
import com.omooooori.model.GithubUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserListViewModel(
    private val fetchUsersUseCase: FetchUsersUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UserListUiState>(UserListUiState.Loading)
    val uiState: StateFlow<UserListUiState> = _uiState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    val usersFlow: Flow<PagingData<GithubUser>> =
        Pager(
            config = PagingConfig(pageSize = PAGE_COUNT),
            pagingSourceFactory = {
                GithubUserPagingSource(fetchUsersUseCase)
            },
        ).flow
            .cachedIn(viewModelScope)

    init {
        _uiState.value = UserListUiState.Success
    }

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    companion object {
        private const val PAGE_COUNT = 20
    }
}
