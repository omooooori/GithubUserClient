package com.omooooori.api.repository

import com.omooooori.api.BASE_URL
import com.omooooori.data.GithubUserDetailResult
import com.omooooori.data.GithubUserEventResult
import com.omooooori.data.GithubUserResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom

interface GithubUserRepository {
    suspend fun fetchUsers(since: Int = 0): List<GithubUserResult>
    suspend fun fetchUserDetail(username: String): GithubUserDetailResult
    suspend fun fetchUserEvents(username: String): List<GithubUserEventResult>

    companion object {
        const val USERS_PATH = "users"
    }
}

class GithubUserApiRepository(
    private val httpClient: HttpClient
) : GithubUserRepository {

    companion object {
        const val TOKEN = "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"
    }

    override suspend fun fetchUsers(since: Int): List<GithubUserResult> {
        return httpClient.get {
            url {
                url.takeFrom(BASE_URL)
                appendPathSegments("users")
                parameters.append("since", since.toString())
            }
            headers {
                append(HttpHeaders.Authorization, "Bearer $TOKEN")
                append(HttpHeaders.Accept, "application/vnd.github+json")
                append("X-GitHub-Api-Version", "2022-11-28")
            }
        }.body()
    }

    override suspend fun fetchUserDetail(username: String): GithubUserDetailResult {
        return httpClient.get {
            url {
                url.takeFrom(BASE_URL)
                appendPathSegments("users", username)
            }
            headers {
                append(HttpHeaders.Authorization, "Bearer $TOKEN")
                append(HttpHeaders.Accept, "application/vnd.github+json")
                append("X-GitHub-Api-Version", "2022-11-28")
            }
        }.body()
    }

    override suspend fun fetchUserEvents(username: String): List<GithubUserEventResult> {
        return httpClient.get {
            url {
                url.takeFrom(BASE_URL)
                appendPathSegments("users", username, "events", "public")
            }
            headers {
                append(HttpHeaders.Authorization, "Bearer $TOKEN")
                append(HttpHeaders.Accept, "application/vnd.github+json")
                append("X-GitHub-Api-Version", "2022-11-28")
            }
        }.body()
    }
}

