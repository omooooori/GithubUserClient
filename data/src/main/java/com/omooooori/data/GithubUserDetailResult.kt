package com.omooooori.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubUserDetailResult(
    val login: String,
    val id: Int,
    val name: String? = null,
    val company: String? = null,
    val location: String? = null,
    @SerialName("public_repos") val publicRepos: Int,
    val followers: Int,
    val following: Int
)
