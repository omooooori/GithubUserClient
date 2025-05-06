package com.omooooori.domain

import com.omooooori.api.repository.GithubUserRepository
import com.omooooori.data.GithubUserEventResult

class FetchUserEventsUseCase(private val repository: GithubUserRepository) {
    suspend fun execute(username: String): List<GithubUserEventResult> = repository.fetchUserEvents(username)
}
