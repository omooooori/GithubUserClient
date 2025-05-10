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

    Given("Fetch events for a specific user") {
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

        When("Repository returns events successfully") {
            coEvery { repository.fetchUserEvents(username) } returns expectedEvents

            Then("Events should be returned correctly") {
                runTest {
                    val result = useCase.execute(username)
                    result shouldBe expectedEvents
                }
            }
        }

        When("Repository returns an empty list") {
            coEvery { repository.fetchUserEvents(username) } returns emptyList()

            Then("Empty list should be returned") {
                runTest {
                    val result = useCase.execute(username)
                    result shouldBe emptyList()
                }
            }
        }
    }
})
