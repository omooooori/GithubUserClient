package com.omooooori.githubuserclient.string

import android.content.Context
import com.omooooori.data.GithubApiError
import com.omooooori.string.ExceptionMessageProvider

class ExceptionMessageProviderImpl(
    private val context: Context
) : ExceptionMessageProvider {

    override fun getMessage(exception: GithubApiError): String {
        return when (exception) {
            is GithubApiError.AuthenticationRequired -> TODO()
            is GithubApiError.Forbidden -> TODO()
            is GithubApiError.NotFound -> TODO()
            is GithubApiError.RateLimitExceeded -> TODO()
            is GithubApiError.ServerError -> TODO()
            is GithubApiError.Unknown -> TODO()
        }
    }
}
