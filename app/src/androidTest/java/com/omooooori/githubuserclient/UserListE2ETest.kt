package com.omooooori.githubuserclient

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.omooooori.feature.userlist.UserListScreen
import com.omooooori.feature.userlist.UserListUiState
import com.omooooori.model.GithubUser
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserListE2ETest {
    @get:Rule
    val composeTestRule = createComposeRule()

    val testUiState =
        UserListUiState.Success(
            query = "",
            users =
                listOf(
                    GithubUser(
                        id = 1,
                        username = "testUser1",
                        avatarUrl = "https://example.com/avatar1.jpg",
                    ),
                    GithubUser(
                        id = 2,
                        username = "testUser2",
                        avatarUrl = "https://example.com/avatar2.jpg",
                    ),
                ),
        )

    @Test
    fun testUserListDisplay() {
        composeTestRule.setContent {
            UserListScreen(
                uiState = testUiState,
            )
        }

        // ユーザーリストが表示されることを確認
        composeTestRule.onNodeWithTag("user_list")
            .assertIsDisplayed()

        // ユーザー項目が表示されることを確認
        composeTestRule.onNodeWithTag("user_item_1")
            .assertIsDisplayed()
    }

    @Test
    fun testUserItemClick() {
        composeTestRule.setContent {
            UserListScreen(uiState = testUiState)
        }

        // ユーザー項目をクリック
        composeTestRule.onNodeWithTag("user_item_1")
            .performClick()
    }
}
