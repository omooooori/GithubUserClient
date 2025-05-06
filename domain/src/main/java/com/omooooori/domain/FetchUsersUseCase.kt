package com.omooooori.domain

import com.omooooori.api.repository.GithubUserRepository
import com.omooooori.data.GithubUserResult

class FetchUsersUseCase(
    private val repository: GithubUserRepository
) {
    suspend fun execute(): List<GithubUserResult> =
        repository.fetchUsers()
}
