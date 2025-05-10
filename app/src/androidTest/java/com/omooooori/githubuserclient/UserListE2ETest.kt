package com.omooooori.githubuserclient

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.omooooori.feature.userlist.UserListScreen
import com.omooooori.feature.userlist.UserListUiState
import com.omooooori.model.GithubUser
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

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
        var clickedUsername: String? = null
        var clickedAvatarUrl: String? = null

        composeTestRule.setContent {
            UserListScreen(
                uiState = testUiState,
                onUserClick = { username, avatarUrl ->
                    clickedUsername = username
                    clickedAvatarUrl = avatarUrl
                },
            )
        }

        composeTestRule.onNodeWithTag("user_item_1")
            .performClick()

        assertEquals("testUser1", clickedUsername)
        assertEquals("https://example.com/avatar1.jpg", clickedAvatarUrl)
    }
}
