package com.omooooori.model

data class GithubUserDetail(
    val id: Int,
    val username: String,
    val name: String?,
    val company: String?,
    val location: String?,
    val publicRepos: Int,
    val followers: Int,
    val following: Int,
)
