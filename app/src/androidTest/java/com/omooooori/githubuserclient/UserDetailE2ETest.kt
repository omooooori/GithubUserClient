package com.omooooori.githubuserclient

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.omooooori.feature.userdetail.UserDetailScreen
import com.omooooori.feature.userdetail.UserDetailUiState
import com.omooooori.model.GithubUserDetail
import com.omooooori.model.GithubUserEvent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDetailE2ETest {
    @get:Rule
    val composeTestRule = createComposeRule()

    val testUiState =
        UserDetailUiState.Success(
            avatarUrl = "https://example.com/avatar1.jpg",
            userDetail =
                GithubUserDetail(
                    id = 1,
                    username = "testUser1",
                    name = "testName",
                    company = "testCompany",
                    location = "Japan",
                    publicRepos = 0,
                    followers = 100,
                    following = 150,
                ),
            events =
                listOf(
                    GithubUserEvent(
                        id = "1",
                        type = "type",
                        repoName = "repo1",
                    ),
                ),
        )

    @Test
    fun testUserDetailDisplay() {
        composeTestRule.setContent {
            UserDetailScreen(
                uiState = testUiState,
            )
        }

        // ユーザー詳細情報が表示されることを確認
        composeTestRule.onNodeWithTag("user_detail_container")
            .assertIsDisplayed()

        // ユーザー名が表示されることを確認
        composeTestRule.onNodeWithTag("username")
            .assertIsDisplayed()
            .assertTextEquals("testUser1")

        // 会社名が表示されることを確認
        composeTestRule.onNodeWithTag("company")
            .assertIsDisplayed()
            .assertTextEquals("会社: testCompany")

        // 場所が表示されることを確認
        composeTestRule.onNodeWithTag("location")
            .assertIsDisplayed()
            .assertTextEquals("場所: Japan")

        // リポジトリ数が表示されることを確認
        composeTestRule.onNodeWithTag("public_repos")
            .assertIsDisplayed()
            .assertTextEquals("リポジトリ数: 0")

        // フォロワー/フォロー数が表示されることを確認
        composeTestRule.onNodeWithTag("followers_following")
            .assertIsDisplayed()
            .assertTextEquals("フォロワー: 100 / フォロー中: 150")

        // イベントリストが表示されることを確認
        composeTestRule.onNodeWithTag("event_list")
            .assertIsDisplayed()

        // イベントの詳細が表示されることを確認
        composeTestRule.onNodeWithTag("event_item_1")
            .assertIsDisplayed()
            .assertTextEquals("・type @ repo1")
    }

    @Test
    fun testBackNavigation() {
        composeTestRule.setContent {
            UserDetailScreen(uiState = testUiState)
        }

        // 戻るボタンをクリック
        composeTestRule.onNodeWithTag("back_button")
            .performClick()
    }
}
