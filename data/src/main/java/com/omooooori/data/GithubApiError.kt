package com.omooooori.data

@Suppress("JavaIoSerializableObjectMustHaveReadResolve")
sealed class GithubApiError : Exception() {
    object AuthenticationRequired : GithubApiError()
    object Forbidden : GithubApiError()
    object NotFound : GithubApiError()
    object RateLimitExceeded : GithubApiError()
    object ServerError : GithubApiError()
    object Unknown : GithubApiError()
}
