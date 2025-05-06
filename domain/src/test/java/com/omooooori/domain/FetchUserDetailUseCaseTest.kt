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

    Given("特定のユーザーの詳細情報を取得する") {
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

        When("リポジトリが正常にユーザー詳細を返す場合") {
            coEvery { repository.fetchUserDetail(username) } returns expectedUserDetail

            Then("ユーザー詳細が正しく返されること") {
                runTest {
                    val result = useCase.execute(username)
                    result shouldBe expectedUserDetail
                }
            }
        }
    }
})
