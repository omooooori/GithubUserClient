package com.omooooori.api.repository

import com.omooooori.api.BASE_URL
import com.omooooori.api.BuildConfig
import com.omooooori.data.GithubUserDetailResult
import com.omooooori.data.GithubUserEventResult
import com.omooooori.data.GithubUserResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMessageBuilder
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom

interface GithubUserRepository {
    suspend fun fetchUsers(since: Int = 0): List<GithubUserResult>

    suspend fun fetchUserDetail(username: String): GithubUserDetailResult

    suspend fun fetchUserEvents(username: String): List<GithubUserEventResult>

    companion object {
        const val HTTP_HEADER_AUTHORIZATION_BEARER = "Bearer"
        const val HTTP_HEADER_ACCEPT_VALUE = "application/vnd.github+json"
        const val HTTP_HEADER_X_GITHUB_API_VERSION_NAME = "X-GitHub-Api-Version"
        const val HTTP_HEADER_X_GITHUB_API_VERSION_VALUE = "2022-11-28"

        const val USERS_PATH = "users"
        const val USERS_EVENT_EVENTS_PATH = "events"
        const val USERS_EVENT_PUBLIC_PATH = "public"
    }
}

class GithubUserApiRepository(
    private val httpClient: HttpClient,
) : GithubUserRepository {
    companion object {
        const val TOKEN = BuildConfig.GITHUB_TOKEN
    }

    override suspend fun fetchUsers(since: Int): List<GithubUserResult> {
        return httpClient.get {
            commonHeaders()
            url {
                url.takeFrom(BASE_URL)
                appendPathSegments(GithubUserRepository.USERS_PATH)
                parameters.append("since", since.toString())
            }
        }.body()
    }

    override suspend fun fetchUserDetail(username: String): GithubUserDetailResult {
        return httpClient.get {
            commonHeaders()
            url {
                url.takeFrom(BASE_URL)
                appendPathSegments(
                    GithubUserRepository.USERS_PATH,
                    username,
                )
            }
        }.body()
    }

    override suspend fun fetchUserEvents(username: String): List<GithubUserEventResult> {
        return httpClient.get {
            commonHeaders()
            url {
                url.takeFrom(BASE_URL)
                appendPathSegments(
                    GithubUserRepository.USERS_PATH,
                    username,
                    GithubUserRepository.USERS_EVENT_EVENTS_PATH,
                    GithubUserRepository.USERS_EVENT_PUBLIC_PATH,
                )
            }
        }.body()
    }

    fun HttpMessageBuilder.commonHeaders() {
        headers {
            append(
                HttpHeaders.Authorization,
                "${GithubUserRepository.HTTP_HEADER_AUTHORIZATION_BEARER} $TOKEN",
            )
            append(
                HttpHeaders.Accept,
                GithubUserRepository.HTTP_HEADER_ACCEPT_VALUE,
            )
            append(
                GithubUserRepository.HTTP_HEADER_X_GITHUB_API_VERSION_NAME,
                GithubUserRepository.HTTP_HEADER_X_GITHUB_API_VERSION_VALUE,
            )
        }
    }
}
