package com.omooooori.feature.userlist

import app.cash.turbine.test
import com.omooooori.data.GithubUserResult
import com.omooooori.data.mapper.toModel
import com.omooooori.domain.FetchUsersUseCase
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
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

            coEvery { fetchUsersUseCase.execute() } returns expectedUsers

            Then("ローディング状態から成功状態に遷移すること") {
                runTest {
                    val viewModel = UserListViewModel(fetchUsersUseCase)
                    viewModel.uiState.test {
                        awaitItem() shouldBe UserListUiState.Loading
                        awaitItem() shouldBe
                            UserListUiState.Success(
                                query = "",
                                users = expectedUsers.map { it.toModel() },
                            )
                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }
        }

        When("エラーが発生した場合") {
            coEvery { fetchUsersUseCase.execute() } throws Exception("エラーが発生しました")

            Then("エラー状態に遷移すること") {
                runTest {
                    val viewModel = UserListViewModel(fetchUsersUseCase)
                    viewModel.uiState.test {
                        awaitItem() shouldBe UserListUiState.Loading
                        awaitItem() shouldBe UserListUiState.Error("エラーが発生しました")
                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }
        }

        When("検索クエリが更新された場合") {
            val query = "test"
            coEvery { fetchUsersUseCase.execute() } returns emptyList()

            Then("UIStateのqueryが更新されること") {
                runTest {
                    val viewModel = UserListViewModel(fetchUsersUseCase)
                    viewModel.uiState.test {
                        awaitItem() shouldBe UserListUiState.Loading
                        awaitItem() shouldBe UserListUiState.Success(query = "", users = emptyList())

                        viewModel.updateQuery(query)
                        awaitItem() shouldBe UserListUiState.Success(query = query, users = emptyList())

                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }
        }
    }
})
