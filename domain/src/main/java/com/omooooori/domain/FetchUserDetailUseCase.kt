package com.omooooori.domain

import com.omooooori.api.repository.GithubUserRepository
import com.omooooori.data.GithubUserDetailResult

class FetchUserDetailUseCase(
    private val repository: GithubUserRepository,
) {
    suspend fun execute(username: String): GithubUserDetailResult = repository.fetchUserDetail(username)
}
