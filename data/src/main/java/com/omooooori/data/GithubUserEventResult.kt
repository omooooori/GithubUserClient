package com.omooooori.data

import kotlinx.serialization.Serializable

@Serializable
data class GithubUserEventResult(
    val id: String,
    val type: String,
    val repo: Repo,
) {
    @Serializable
    data class Repo(
        val name: String,
    )
}
