package com.omooooori.string

import com.omooooori.data.GithubApiError

interface ExceptionMessageProvider {
    fun getMessage(exception: GithubApiError): String
}
