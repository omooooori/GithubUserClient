package com.omooooori.feature.userdetail

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
import androidx.compose.ui.unit.dp
import com.omooooori.design.component.material3.AppScaffold
import com.omooooori.design.component.material3.AppText
import com.omooooori.design.component.material3.AppTopBar
import com.omooooori.design.component.other.AppAsyncImage
import com.omooooori.model.GithubUserDetail
import com.omooooori.model.GithubUserEvent

@Composable
fun UserDetailScreen(uiState: UserDetailUiState) {
    AppScaffold(
        topBar = {
            AppTopBar(title = "GitHub User Detail")
        },
    ) { paddingValues ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
        ) {
            when (val state = uiState) {
                is UserDetailUiState.Idle -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        AppText(text = "ユーザーを選択してください")
                    }
                }

                is UserDetailUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
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
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        AppText(
                            "エラー: ${state.message}",
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
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
    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AppAsyncImage(
                    model = avatarUrl,
                    contentDescription = null,
                    modifier =
                        Modifier
                            .size(64.dp)
                            .clip(CircleShape),
                )
                Spacer(modifier = Modifier.width(16.dp))
                AppText(text = user.username, style = MaterialTheme.typography.titleLarge)
            }
            Spacer(modifier = Modifier.height(16.dp))
            AppText("会社: ${user.company ?: "未登録"}")
            AppText("場所: ${user.location ?: "未登録"}")
            AppText("リポジトリ数: ${user.publicRepos}")
            AppText("フォロワー: ${user.followers} / フォロー中: ${user.following}")
            Spacer(modifier = Modifier.height(24.dp))
            AppText("最近のアクティビティ", style = MaterialTheme.typography.titleMedium)
        }

        items(events) { event ->
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                AppText("・${event.type} @ ${event.repoName}")
            }
        }
    }
}
