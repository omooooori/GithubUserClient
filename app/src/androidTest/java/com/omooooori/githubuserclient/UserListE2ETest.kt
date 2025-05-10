package com.omooooori.githubuserclient

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.collectAsLazyPagingItems
import com.omooooori.feature.userlist.UserListScreenContent
import com.omooooori.model.GithubUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class UserListE2ETest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val testUsers =
        listOf(
            GithubUser(1, "testUser1", "https://example.com/avatar1.jpg"),
            GithubUser(2, "testUser2", "https://example.com/avatar2.jpg"),
        )

    private fun createTestPager(): Pager<Int, GithubUser> {
        return Pager(PagingConfig(pageSize = 10)) {
            object : PagingSource<Int, GithubUser>() {
                override fun getRefreshKey(state: PagingState<Int, GithubUser>): Int? = null

                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubUser> {
                    return LoadResult.Page(
                        data = testUsers,
                        prevKey = null,
                        nextKey = null,
                    )
                }
            }
        }
    }

    @Composable
    private fun UserListTestHost(
        onUserClick: (String, String) -> Unit = { _, _ -> },
    ) {
        val pager = remember { createTestPager() }
        val items = pager.flow.collectAsLazyPagingItems()

        UserListScreenContent(
            query = "",
            onQueryChange = {},
            users = items,
            onUserClick = onUserClick,
        )
    }

    @Test
    fun testUserListDisplay() {
        composeTestRule.setContent {
            UserListTestHost()
        }

        // 表示待機（確実にレイアウトが完了してからチェック）
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithTag("user_item_0").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithTag("user_list").assertIsDisplayed()
        composeTestRule.onNodeWithTag("user_item_0").assertIsDisplayed()
        composeTestRule.onNodeWithTag("user_item_1").assertIsDisplayed()
    }

    @Test
    fun testUserItemClick() {
        var clickedUsername: String? = null
        var clickedAvatarUrl: String? = null

        composeTestRule.setContent {
            UserListTestHost { username, avatarUrl ->
                clickedUsername = username
                clickedAvatarUrl = avatarUrl
            }
        }

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithTag("user_item_0").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithTag("user_item_0").performClick()

        assertEquals("testUser1", clickedUsername)
        assertEquals("https://example.com/avatar1.jpg", clickedAvatarUrl)
    }
}
