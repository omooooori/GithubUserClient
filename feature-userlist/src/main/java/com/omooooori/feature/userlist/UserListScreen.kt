package com.omooooori.feature.userlist

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.omooooori.design.component.material3.AppErrorDialog
import com.omooooori.design.component.material3.AppScaffold
import com.omooooori.design.component.material3.AppText
import com.omooooori.design.component.material3.AppTopBar
import com.omooooori.design.theme.GithubUserClientTheme
import com.omooooori.design.ui.SearchInputBar
import com.omooooori.design.ui.SearchListCell
import com.omooooori.model.GithubUser
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserListScreen(
    viewModel: UserListViewModel = koinViewModel(),
    onUserClick: (String, String) -> Unit = { _, _ -> },
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val query by viewModel.query.collectAsStateWithLifecycle()
    val pagingItems = viewModel.usersFlow.collectAsLazyPagingItems()

    if (uiState == UserListUiState.Success) {
        UserListScreenContent(
            query = query,
            onQueryChange = viewModel::updateQuery,
            users = pagingItems,
            onUserClick = onUserClick,
        )
    }
}

@Composable
fun UserListScreenContent(
    query: String,
    onQueryChange: (String) -> Unit,
    users: LazyPagingItems<GithubUser>,
    onUserClick: (String, String) -> Unit = { _, _ -> },
) {
    val loadState = users.loadState.refresh

    AppScaffold(
        topBar = {
            AppTopBar(title = "GitHub User List App")
        },
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .testTag("user_list_container"),
        ) {
            SearchInputBar(
                query = query,
                onQueryChange = onQueryChange,
                placeholder = "Search GitHub users...",
            )

            when (loadState) {
                is LoadState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier =
                                Modifier.align(
                                    Alignment.Center,
                                ),
                        )
                    }
                }

                is LoadState.Error -> {
                    AppErrorDialog(
                        message = "An unexpected error occurred",
                    ) {}
                }

                is LoadState.NotLoading -> {
                    LazyColumn(
                        contentPadding = PaddingValues(8.dp),
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .testTag("user_list"),
                    ) {
                        items(users.itemCount) { index ->
                            val user = users[index]
                            if (user != null && user.username.contains(query, ignoreCase = true)) {
                                SearchListCell(
                                    username = user.username,
                                    avatarUrl = user.avatarUrl,
                                    onClick = { onUserClick(user.username, user.avatarUrl) },
                                    modifier = Modifier.testTag("user_item_$index"),
                                )
                            }
                        }

                        when (users.loadState.append) {
                            is LoadState.Loading ->
                                item {
                                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                                }

                            is LoadState.Error ->
                                item {
                                    AppText("Failed to load more items")
                                }

                            else -> {}
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun rememberFakeLazyPagingItems(users: List<GithubUser>): Flow<PagingData<GithubUser>> {
    val pagingSource =
        object : PagingSource<Int, GithubUser>() {
            override fun getRefreshKey(state: PagingState<Int, GithubUser>): Int? = null

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubUser> {
                return LoadResult.Page(users, prevKey = null, nextKey = null)
            }
        }
    val pager = Pager(PagingConfig(10)) { pagingSource }
    return pager.flow
}

val previewUsersList =
    listOf(
        GithubUser(1, "omooooori", "https://avatars.githubusercontent.com/u/583231?v=4"),
        GithubUser(2, "omooooori2", "https://avatars.githubusercontent.com/u/1024025?v=4"),
        GithubUser(3, "omooooori3", "https://avatars.githubusercontent.com/u/66577?v=4"),
    )

@Composable
fun previewLazyPagingItems(users: List<GithubUser>): LazyPagingItems<GithubUser> {
    return rememberFakeLazyPagingItems(users).collectAsLazyPagingItems()
}

@Preview(
    name = "UserListScreen - Success Light",
    showBackground = true,
)
@Composable
fun PreviewUserListScreenSuccessLight() {
    val users = previewLazyPagingItems(previewUsersList)
    GithubUserClientTheme(darkTheme = false) {
        UserListScreenContent(
            query = "",
            onQueryChange = {},
            users = users,
        )
    }
}

@Preview(
    name = "UserListScreen - Success Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewUserListScreenSuccessDark() {
    val users = previewLazyPagingItems(previewUsersList)
    GithubUserClientTheme(darkTheme = true) {
        UserListScreenContent(
            query = "",
            onQueryChange = {},
            users = users,
        )
    }
}
