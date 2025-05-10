package com.omooooori.feature.userlist

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.omooooori.design.component.material3.AppErrorDialog
import com.omooooori.design.component.material3.AppScaffold
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
    UserListScreenContent(
        uiState = uiState,
        onQueryChange = { viewModel.updateQuery(it) },
        onUserClick = onUserClick,
    )
}

@Composable
fun UserListScreenContent(
    uiState: UserListUiState,
    onQueryChange: (String) -> Unit = {},
    onUserClick: (String, String) -> Unit = { _, _ -> },
) {
    AppScaffold(
        topBar = {
            AppTopBar(title = "GitHub User List App")
        },
    ) { paddingValues ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .testTag("user_list_container"),
        ) {
            when (uiState) {
                is UserListUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier =
                            Modifier
                                .align(Alignment.Center)
                                .testTag("loading_indicator"),
                    )
                }

                is UserListUiState.Error -> {
                    AppErrorDialog(
                        message = uiState.message,
                    ) {}
                }

                is UserListUiState.Success -> {
                    val users = uiState.users.collectAsLazyPagingItems()
                    Column {
                        SearchInputBar(
                            query = uiState.query,
                            onQueryChange = { onQueryChange(it) },
                            placeholder = "Search GitHub users...",
                            modifier = Modifier.testTag("search_bar"),
                        )

                        LazyColumn(
                            contentPadding = PaddingValues(vertical = 4.dp),
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            items(users.itemCount) { index ->
                                val user = users[index]
                                user?.let {
                                    SearchListCell(
                                        username = it.username,
                                        avatarUrl = it.avatarUrl,
                                        onClick = { onUserClick(it.username, it.avatarUrl) },
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun rememberFakeLazyPagingItems(users: List<GithubUser>): Flow<PagingData<GithubUser>> {
    val pagingSource = object : PagingSource<Int, GithubUser>() {
        override fun getRefreshKey(state: PagingState<Int, GithubUser>): Int? = null
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubUser> {
            return LoadResult.Page(users, prevKey = null, nextKey = null)
        }
    }
    val pager = Pager(PagingConfig(10)) { pagingSource }
    return pager.flow
}

val previewUsersList = listOf(
    GithubUser(1, "omooooori", "https://avatars.githubusercontent.com/u/583231?v=4"),
    GithubUser(2, "omooooori2", "https://avatars.githubusercontent.com/u/1024025?v=4"),
    GithubUser(3, "omooooori3", "https://avatars.githubusercontent.com/u/66577?v=4")
)

@Preview(name = "UserListScreen - Success Light", showBackground = true)
@Composable
fun PreviewUserListScreenSuccessLight() {
    GithubUserClientTheme(darkTheme = false) {
        UserListScreenContent(
            uiState = UserListUiState.Success(
                users = rememberFakeLazyPagingItems(previewUsersList)
            ),
        )
    }
}

@Preview(name = "UserListScreen - Success Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewUserListScreenSuccessDark() {
    val users = rememberFakeLazyPagingItems(previewUsersList)
    GithubUserClientTheme(darkTheme = true) {
        UserListScreenContent(
            uiState = UserListUiState.Success(
                users = rememberFakeLazyPagingItems(previewUsersList)
            ),
        )
    }
}


@Preview(name = "UserListScreen - Loading Light", showBackground = true)
@Composable
fun PreviewUserListScreenLoadingLight() {
    val users = rememberFakeLazyPagingItems(emptyList())
    GithubUserClientTheme(darkTheme = false) {
        UserListScreenContent(
            uiState = UserListUiState.Success(
                users = rememberFakeLazyPagingItems(previewUsersList)
            ),
        )
    }
}

@Preview(name = "UserListScreen - Loading Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewUserListScreenLoadingDark() {
    val users = rememberFakeLazyPagingItems(emptyList())
    GithubUserClientTheme(darkTheme = true) {
        UserListScreenContent(
            uiState = UserListUiState.Success(
                users = rememberFakeLazyPagingItems(previewUsersList)
            ),
        )
    }
}

@Preview(name = "UserListScreen - Error Light", showBackground = true)
@Composable
fun PreviewUserListScreenErrorLight() {
    val users = rememberFakeLazyPagingItems(emptyList())
    GithubUserClientTheme(darkTheme = false) {
        UserListScreenContent(
            uiState = UserListUiState.Success(
                users = rememberFakeLazyPagingItems(previewUsersList)
            ),
        )
    }
}

@Preview(name = "UserListScreen - Error Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewUserListScreenErrorDark() {
    val users = rememberFakeLazyPagingItems(emptyList())
    GithubUserClientTheme(darkTheme = true) {
        UserListScreenContent(
            uiState = UserListUiState.Success(
                users = rememberFakeLazyPagingItems(previewUsersList)
            ),
        )
    }
}
