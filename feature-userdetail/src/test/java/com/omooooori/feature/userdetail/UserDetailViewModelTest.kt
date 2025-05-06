package com.omooooori.feature.userdetail

import app.cash.turbine.test
import com.omooooori.data.GithubUserDetailResult
import com.omooooori.data.GithubUserEventResult
import com.omooooori.data.mapper.toModel
import com.omooooori.domain.FetchUserDetailUseCase
import com.omooooori.domain.FetchUserEventsUseCase
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
class UserDetailViewModelTest : BehaviorSpec({
    val testDispatcher = StandardTestDispatcher()
    val fetchUserDetailUseCase: FetchUserDetailUseCase = mockk()
    val fetchUserEventsUseCase: FetchUserEventsUseCase = mockk()

    beforeTest {
        Dispatchers.setMain(testDispatcher)
    }

    afterTest {
        Dispatchers.resetMain()
    }

    Given("ユーザー詳細画面のViewModel") {
        val username = "testuser"
        val avatarUrl = "https://example.com/avatar"

        When("ユーザー詳細の読み込みを開始した場合") {
            val expectedUserDetail =
                GithubUserDetailResult(
                    id = 1,
                    login = username,
                    name = "Test User",
                    company = "Test Company",
                    location = "Tokyo",
                    publicRepos = 10,
                    followers = 100,
                    following = 50,
                )

            val expectedEvents =
                listOf(
                    GithubUserEventResult(
                        id = "1",
                        type = "PushEvent",
                        repo =
                            GithubUserEventResult.Repo(
                                name = "test/repo",
                            ),
                    ),
                    GithubUserEventResult(
                        id = "2",
                        type = "CreateEvent",
                        repo =
                            GithubUserEventResult.Repo(
                                name = "test/repo2",
                            ),
                    ),
                )

            coEvery { fetchUserDetailUseCase.execute(username) } returns expectedUserDetail
            coEvery { fetchUserEventsUseCase.execute(username) } returns expectedEvents

            Then("ローディング状態から成功状態に遷移すること") {
                runTest {
                    val viewModel = UserDetailViewModel(fetchUserDetailUseCase, fetchUserEventsUseCase)
                    viewModel.uiState.test {
                        awaitItem() shouldBe UserDetailUiState.Idle

                        viewModel.load(username, avatarUrl)
                        awaitItem() shouldBe UserDetailUiState.Loading
                        awaitItem() shouldBe
                            UserDetailUiState.Success(
                                avatarUrl = avatarUrl,
                                userDetail = expectedUserDetail.toModel(),
                                events = expectedEvents.map { it.toModel() },
                            )
                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }
        }

        When("エラーが発生した場合") {
            coEvery { fetchUserDetailUseCase.execute(username) } throws Exception("エラーが発生しました")

            Then("エラー状態に遷移すること") {
                runTest {
                    val viewModel = UserDetailViewModel(fetchUserDetailUseCase, fetchUserEventsUseCase)
                    viewModel.uiState.test {
                        awaitItem() shouldBe UserDetailUiState.Idle

                        viewModel.load(username, avatarUrl)
                        awaitItem() shouldBe UserDetailUiState.Loading
                        awaitItem() shouldBe UserDetailUiState.Error("ユーザー詳細の取得に失敗しました")
                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }
        }
    }
})
