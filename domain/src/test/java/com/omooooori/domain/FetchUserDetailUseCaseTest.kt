package com.omooooori.domain

import com.omooooori.api.repository.GithubUserRepository
import com.omooooori.data.GithubUserDetailResult
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class FetchUserDetailUseCaseTest : BehaviorSpec({
    val repository: GithubUserRepository = mockk()
    val useCase = FetchUserDetailUseCase(repository)

    Given("Fetch user details for a specific user") {
        val username = "testuser"
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

        When("Repository returns user details successfully") {
            coEvery { repository.fetchUserDetail(username) } returns expectedUserDetail

            Then("User details should be returned correctly") {
                runTest {
                    val result = useCase.execute(username)
                    result shouldBe expectedUserDetail
                }
            }
        }
    }
})
