package com.omooooori.domain

import com.omooooori.api.repository.GithubUserRepository
import com.omooooori.data.GithubUserEventResult
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class FetchUserEventsUseCaseTest : BehaviorSpec({
    val repository: GithubUserRepository = mockk()
    val useCase = FetchUserEventsUseCase(repository)

    Given("特定のユーザーのイベント一覧を取得する") {
        val username = "testuser"
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

        When("リポジトリが正常にイベント一覧を返す場合") {
            coEvery { repository.fetchUserEvents(username) } returns expectedEvents

            Then("イベント一覧が正しく返されること") {
                runTest {
                    val result = useCase.execute(username)
                    result shouldBe expectedEvents
                }
            }
        }

        When("リポジトリが空のリストを返す場合") {
            coEvery { repository.fetchUserEvents(username) } returns emptyList()

            Then("空のリストが返されること") {
                runTest {
                    val result = useCase.execute(username)
                    result shouldBe emptyList()
                }
            }
        }
    }
})
