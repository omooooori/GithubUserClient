package com.omooooori.feature.userlist

import app.cash.turbine.test
import com.omooooori.data.GithubUserResult
import com.omooooori.domain.FetchUsersUseCase
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class UserListViewModelTest : BehaviorSpec({

    val testDispatcher = StandardTestDispatcher()
    val fetchUsersUseCase: FetchUsersUseCase = mockk()

    beforeTest {
        Dispatchers.setMain(testDispatcher)
    }

    afterTest {
        Dispatchers.resetMain()
    }

    Given("ユーザー一覧画面のViewModel") {
        When("初期化時") {
            val expectedUsers =
                listOf(
                    GithubUserResult(
                        id = 1,
                        login = "user1",
                        avatarUrl = "https://example.com/avatar1",
                    ),
                    GithubUserResult(
                        id = 2,
                        login = "user2",
                        avatarUrl = "https://example.com/avatar2",
                    ),
                )

            coEvery { fetchUsersUseCase.execute(any()) } returns expectedUsers

            Then("uiStateが即Successになること") {
                runTest(testDispatcher) {
                    val viewModel = UserListViewModel(fetchUsersUseCase)

                    viewModel.uiState.test {
                        awaitItem() shouldBe UserListUiState.Success
                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }

            Then("usersFlowがPagingDataをemitすること") {
                runTest(testDispatcher) {
                    val viewModel = UserListViewModel(fetchUsersUseCase)

                    viewModel.usersFlow.test {
                        val pagingData = awaitItem()
                        pagingData shouldNotBe null
                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }
        }

        When("クエリを更新した場合") {
            coEvery { fetchUsersUseCase.execute(any()) } returns emptyList()

            Then("query Flowが更新されること") {
                runTest(testDispatcher) {
                    val viewModel = UserListViewModel(fetchUsersUseCase)

                    viewModel.query.test {
                        awaitItem() shouldBe ""
                        viewModel.updateQuery("test")
                        awaitItem() shouldBe "test"
                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }
        }
    }
})
