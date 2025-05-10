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

        // Verify that user detail information is displayed
        composeTestRule.onNodeWithTag("user_detail_container")
            .assertIsDisplayed()

        // Verify that the username is displayed
        composeTestRule.onNodeWithTag("username")
            .assertIsDisplayed()
            .assertTextEquals("testUser1")

        // Verify that the company name is displayed
        composeTestRule.onNodeWithTag("company")
            .assertIsDisplayed()
            .assertTextEquals("Company: testCompany")

        // Verify that the location is displayed
        composeTestRule.onNodeWithTag("location")
            .assertIsDisplayed()
            .assertTextEquals("Location: Japan")

        // Verify that the number of repositories is displayed
        composeTestRule.onNodeWithTag("public_repos")
            .assertIsDisplayed()
            .assertTextEquals("Repositories: 0")

        // Verify that the number of followers/following is displayed
        composeTestRule.onNodeWithTag("followers_following")
            .assertIsDisplayed()
            .assertTextEquals("Followers: 100 / Following: 150")

        // Verify that the event list is displayed
        composeTestRule.onNodeWithTag("event_list")
            .assertIsDisplayed()

        // Verify that the event details are displayed
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
