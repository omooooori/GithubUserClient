package com.omooooori.data

sealed class GithubApiError : Exception() {
    data class AuthenticationRequired(
        override val message: String = "Authentication required"
    ) : GithubApiError()

    data class Forbidden(
        override val message: String = "Access forbidden"
    ) : GithubApiError()

    data class NotFound(
        override val message: String = "Resource not found"
    ) : GithubApiError()

    data class RateLimitExceeded(
        override val message: String = "API rate limit exceeded"
    ) : GithubApiError()

    data class ServerError(
        override val message: String = "Server error occurred"
    ) : GithubApiError()

    data class Unknown(
        override val message: String = "An unexpected error occurred"
    ) : GithubApiError()
}
