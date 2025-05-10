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

    Given("ユーザー一覧を取得する") {
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

        When("リポジトリが正常にユーザー一覧を返す場合") {
            coEvery { repository.fetchUsers(pageCount) } returns expectedUsers

            Then("ユーザー一覧が正しく返されること") {
                runTest {
                    val result = useCase.execute(pageCount)
                    result shouldBe expectedUsers
                }
            }
        }

        When("リポジトリが空のリストを返す場合") {
            coEvery { repository.fetchUsers(pageCount) } returns emptyList()

            Then("空のリストが返されること") {
                runTest {
                    val result = useCase.execute(pageCount)
                    result shouldBe emptyList()
                }
            }
        }
    }
})
