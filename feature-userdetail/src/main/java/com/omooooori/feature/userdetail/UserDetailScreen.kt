package com.omooooori.feature.userdetail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omooooori.design.component.material3.AppErrorDialog
import com.omooooori.design.component.material3.AppScaffold
import com.omooooori.design.component.material3.AppText
import com.omooooori.design.component.material3.AppTopBar
import com.omooooori.design.component.other.AppAsyncImage
import com.omooooori.design.theme.GithubUserClientTheme
import com.omooooori.model.GithubUserDetail
import com.omooooori.model.GithubUserEvent

@Composable
fun UserDetailScreen(uiState: UserDetailUiState) {
    AppScaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.user_detail_screen_title),
                modifier = Modifier.testTag("back_button"),
            )
        },
    ) { paddingValues ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .testTag("user_detail_container"),
        ) {
            when (val state = uiState) {
                is UserDetailUiState.Idle -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        AppText(
                            text = stringResource(R.string.user_detail_user_not_selected_message),
                            modifier = Modifier.testTag("idle_message"),
                        )
                    }
                }

                is UserDetailUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.testTag("loading_indicator"),
                        )
                    }
                }

                is UserDetailUiState.Success -> {
                    UserDetailContent(
                        avatarUrl = state.avatarUrl,
                        user = state.userDetail,
                        events = state.events,
                    )
                }

                is UserDetailUiState.Error -> {
                    AppErrorDialog(
                        message = state.message,
                    ) {}
                }
            }
        }
    }
}

@Composable
fun UserDetailContent(
    avatarUrl: String,
    user: GithubUserDetail,
    events: List<GithubUserEvent>,
) {
    val notRegistered = stringResource(R.string.user_detail_not_registered)
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .testTag("user_detail_content"),
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AppAsyncImage(
                    model = avatarUrl,
                    contentDescription = null,
                    modifier =
                        Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .testTag("avatar_image"),
                )
                Spacer(modifier = Modifier.width(16.dp))
                AppText(
                    text = user.username,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.testTag("username"),
                )
            }

            AppText(
                text = stringResource(
                    R.string.user_detail_company,
                    user.company ?: notRegistered
                ),
                modifier = Modifier.testTag("company"),
            )

            AppText(
                text = stringResource(
                    R.string.user_detail_location,
                    user.location ?: notRegistered
                ),
                modifier = Modifier.testTag("location"),
            )

            AppText(
                text = stringResource(
                    R.string.user_detail_location,
                    user.location ?: notRegistered
                ),
                modifier = Modifier.testTag("location"),
            )

            AppText(
                text = stringResource(
                    R.string.user_detail_repositories,
                    user.publicRepos
                ),
                modifier = Modifier.testTag("public_repos"),
            )

            AppText(
                text = stringResource(
                    R.string.user_detail_followers_following,
                    user.followers,
                    user.following
                ),
                modifier = Modifier.testTag("followers_following"),
            )

            AppText(
                text = stringResource(R.string.user_detail_recent_activity),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.testTag("activity_title"),
            )
        }

        items(events) { event ->
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .testTag("event_list"),
            ) {
                AppText(
                    text = stringResource(
                        id = R.string.user_detail_event_item,
                        event.type,
                        event.repoName
                    ),
                    modifier = Modifier.testTag("event_item_${event.id}"),
                )
            }
        }
    }
}

private val previewUiState =
    UserDetailUiState.Success(
        avatarUrl = "https://avatars.githubusercontent.com/u/583231?v=4",
        userDetail =
            GithubUserDetail(
                id = 1,
                username = "omooooori",
                name = "omooooori name",
                company = "Company Name",
                location = "Japan",
                publicRepos = 50,
                followers = 100,
                following = 20,
            ),
        events = emptyList(),
    )

@Preview(
    name = "UserDetailScreen - Light",
    showBackground = true,
    apiLevel = 33,
)
@Composable
fun PreviewUserDetailScreenLight() {
    GithubUserClientTheme(darkTheme = false) {
        UserDetailScreen(
            uiState = previewUiState,
        )
    }
}

@Preview(
    name = "UserDetailScreen - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    apiLevel = 33,
)
@Composable
fun PreviewUserDetailScreenDark() {
    GithubUserClientTheme(darkTheme = true) {
        UserDetailScreen(
            uiState = previewUiState,
        )
    }
}

@Preview(
    name = "UserDetailScreen Loading - Light",
    showBackground = true,
    apiLevel = 33,
)
@Composable
fun PreviewUserDetailScreenLoadingLight() {
    GithubUserClientTheme(darkTheme = false) {
        UserDetailScreen(
            uiState = UserDetailUiState.Loading,
        )
    }
}

@Preview(
    name = "UserDetailScreen Loading - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    apiLevel = 33,
)
@Composable
fun PreviewUserDetailScreenLoadingDark() {
    GithubUserClientTheme(darkTheme = true) {
        UserDetailScreen(
            uiState = UserDetailUiState.Loading,
        )
    }
}

@Preview(
    name = "UserDetailScreen Error - Light",
    showBackground = true,
    apiLevel = 33,
)
@Composable
fun PreviewUserDetailScreenErrorLight() {
    GithubUserClientTheme(darkTheme = false) {
        UserDetailScreen(
            uiState = UserDetailUiState.Error(message = "Failed to load user from Github..."),
        )
    }
}

@Preview(
    name = "UserDetailScreen Error - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    apiLevel = 33,
)
@Composable
fun PreviewUserDetailScreenErrorDark() {
    GithubUserClientTheme(darkTheme = true) {
        UserDetailScreen(
            uiState = UserDetailUiState.Error(message = "Failed to load user from Github..."),
        )
    }
}
