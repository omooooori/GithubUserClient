package com.omooooori.feature.userdetail

import app.cash.turbine.test
import com.omooooori.data.GithubApiError
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

    Given("UserDetailScreen ViewModel") {
        val username = "testuser"
        val avatarUrl = "https://example.com/avatar"

        When("Start loading user details") {
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

            Then("Should transition from loading to success state") {
                runTest {
                    val viewModel = UserDetailViewModel(fetchUserDetailUseCase, fetchUserEventsUseCase)
                    viewModel.uiState.test {
                        awaitItem() shouldBe UserDetailUiState.Idle

                        viewModel.fetchUserDetail(username, avatarUrl)
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

        When("An error occurs") {
            coEvery { fetchUserDetailUseCase.execute(username) } throws GithubApiError.AuthenticationRequired()

            Then("Should transition to error state") {
                runTest {
                    val viewModel = UserDetailViewModel(fetchUserDetailUseCase, fetchUserEventsUseCase)
                    viewModel.uiState.test {
                        awaitItem() shouldBe UserDetailUiState.Idle

                        viewModel.fetchUserDetail(username, avatarUrl)
                        awaitItem() shouldBe UserDetailUiState.Loading
                        awaitItem() shouldBe UserDetailUiState.Error("Authentication required")
                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }
        }
    }
})
