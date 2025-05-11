package com.omooooori.feature.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.omooooori.domain.FetchUsersUseCase
import com.omooooori.model.GithubUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

class UserListViewModel(
    private val fetchUsersUseCase: FetchUsersUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UserListUiState>(UserListUiState.Loading)
    val uiState: StateFlow<UserListUiState> = _uiState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val usersFlow: Flow<PagingData<GithubUser>> =
        query
            .debounce(300)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                Pager(
                    config = PagingConfig(pageSize = GithubUserPagingSource.PAGE_SIZE),
                    pagingSourceFactory = {
                        GithubUserPagingSource(
                            fetchUsersUseCase,
                            query,
                        )
                    },
                ).flow
            }
            .cachedIn(viewModelScope)

    init {
        _uiState.value = UserListUiState.Success
    }

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }
}
