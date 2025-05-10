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
import com.omooooori.design.component.material3.AppErrorDialog
import com.omooooori.design.component.material3.AppScaffold
import com.omooooori.design.component.material3.AppTopBar
import com.omooooori.design.theme.GithubUserClientTheme
import com.omooooori.design.ui.SearchInputBar
import com.omooooori.design.ui.SearchListCell
import com.omooooori.model.GithubUser

@Composable
fun UserListScreen(
    uiState: UserListUiState,
    onUserClick: (String, String) -> Unit = { _, _ -> },
) {
    var query by remember { mutableStateOf("") }

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
                    Column {
                        SearchInputBar(
                            query = query,
                            onQueryChange = { query = it },
                            placeholder = "Search GitHub users...",
                            modifier = Modifier.testTag("search_bar"),
                        )

                        val filtered =
                            uiState.users.filter {
                                it.username.contains(query, ignoreCase = true)
                            }

                        LazyColumn(
                            contentPadding = PaddingValues(vertical = 4.dp),
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .testTag("user_list"),
                        ) {
                            items(filtered, key = { it.id }) { user ->
                                SearchListCell(
                                    username = user.username,
                                    avatarUrl = user.avatarUrl,
                                    onClick = { onUserClick(user.username, user.avatarUrl) },
                                    modifier = Modifier.testTag("user_item_${user.id}"),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private val previewUiState =
    UserListUiState.Success(
        query = "",
        users =
            listOf(
                GithubUser(
                    id = 1,
                    username = "omooooori",
                    avatarUrl = "https://avatars.githubusercontent.com/u/583231?v=4",
                ),
                GithubUser(
                    id = 2,
                    username = "omooooori2",
                    avatarUrl = "https://avatars.githubusercontent.com/u/1024025?v=4",
                ),
                GithubUser(
                    id = 3,
                    username = "omooooori3",
                    avatarUrl = "https://avatars.githubusercontent.com/u/66577?v=4",
                ),
            ),
    )

@Preview(
    name = "UserListScreen - Light",
    showBackground = true,
    apiLevel = 33,
)
@Composable
fun PreviewUserListScreenLight() {
    GithubUserClientTheme(darkTheme = false) {
        UserListScreen(
            uiState = previewUiState,
            onUserClick = { _, _ -> },
        )
    }
}

@Preview(
    name = "UserListScreen - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    apiLevel = 33,
)
@Composable
fun PreviewUserListScreenDark() {
    GithubUserClientTheme(darkTheme = true) {
        UserListScreen(
            uiState = previewUiState,
            onUserClick = { _, _ -> },
        )
    }
}

@Preview(
    name = "UserListScreen Loading - Light",
    showBackground = true,
    apiLevel = 33,
)
@Composable
fun PreviewUserListScreenLoadingLight() {
    GithubUserClientTheme(darkTheme = false) {
        UserListScreen(
            uiState = UserListUiState.Loading,
            onUserClick = { _, _ -> },
        )
    }
}

@Preview(
    name = "UserListScreen Loading - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    apiLevel = 33,
)
@Composable
fun PreviewUserListScreenLoadingDark() {
    GithubUserClientTheme(darkTheme = true) {
        UserListScreen(
            uiState = UserListUiState.Loading,
            onUserClick = { _, _ -> },
        )
    }
}

@Preview(
    name = "UserListScreen Error - Light",
    showBackground = true,
    apiLevel = 33,
)
@Composable
fun PreviewUserListScreenErrorLight() {
    GithubUserClientTheme(darkTheme = false) {
        UserListScreen(
            uiState = UserListUiState.Error(message = "Failed to load user from Github..."),
            onUserClick = { _, _ -> },
        )
    }
}

@Preview(
    name = "UserListScreen Error - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    apiLevel = 33,
)
@Composable
fun PreviewUserListScreenErrorDark() {
    GithubUserClientTheme(darkTheme = true) {
        UserListScreen(
            uiState = UserListUiState.Error(message = "Failed to load user from Github..."),
            onUserClick = { _, _ -> },
        )
    }
}
