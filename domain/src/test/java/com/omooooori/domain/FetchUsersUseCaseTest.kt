package com.omooooori.domain

import com.omooooori.api.repository.GithubUserRepository
import com.omooooori.data.GithubUserResult
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class FetchUsersUseCaseTest : BehaviorSpec({
    val repository: GithubUserRepository = mockk()
    val useCase = FetchUsersUseCase(repository)
    val pageCount = 20

    Given("Fetch list of users") {
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

        When("Repository returns user list successfully") {
            coEvery { repository.fetchUsers(pageCount) } returns expectedUsers

            Then("User list should be returned correctly") {
                runTest {
                    val result = useCase.execute(pageCount)
                    result shouldBe expectedUsers
                }
            }
        }

        When("Repository returns an empty list") {
            coEvery { repository.fetchUsers(pageCount) } returns emptyList()

            Then("Empty list should be returned") {
                runTest {
                    val result = useCase.execute(pageCount)
                    result shouldBe emptyList()
                }
            }
        }
    }
})
