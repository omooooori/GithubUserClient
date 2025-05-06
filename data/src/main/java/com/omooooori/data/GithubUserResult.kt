package com.omooooori.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubUserResult(
    val login: String,
    val id: Int,
    @SerialName("avatar_url") val avatarUrl: String,
)
