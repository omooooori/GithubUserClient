package com.omooooori.api

import com.omooooori.data.GithubApiError
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorApiClient {
    fun httpClient() =
        HttpClient {
            expectSuccess = false
            install(HttpTimeout) {
                val timeout = 60000L
                connectTimeoutMillis = timeout
                requestTimeoutMillis = timeout
                socketTimeoutMillis = timeout
            }

            install(ResponseObserver) {
                onResponse { response ->
                    println("AppDebug HTTP ResponseObserver status: ${response.status.value}")
                }
            }

            HttpResponseValidator {
                validateResponse { response: HttpResponse ->
                    val statusCode = response.status.value
                    when (statusCode) {
                        HttpStatusCode.OK.value -> Unit
                        HttpStatusCode.NotModified.value -> Unit
                        HttpStatusCode.Unauthorized.value -> throw GithubApiError.AuthenticationRequired
                        HttpStatusCode.Forbidden.value -> throw GithubApiError.Forbidden
                        HttpStatusCode.NotFound.value -> throw GithubApiError.NotFound
                        HttpStatusCode.TooManyRequests.value -> throw GithubApiError.RateLimitExceeded
                        in 500..599 -> throw GithubApiError.ServerError
                        else -> throw GithubApiError.Unknown
                    }
                }
            }

            install(Logging) {
                level = LogLevel.ALL
                logger =
                    object : Logger {
                        override fun log(message: String) {
                            println("AppDebug KtorHttpClient message:$message")
                        }
                    }
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    },
                )
            }
        }
}
