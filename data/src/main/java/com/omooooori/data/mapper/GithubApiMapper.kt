package com.omooooori.data.mapper

import com.omooooori.data.GithubUserDetailResult
import com.omooooori.data.GithubUserEventResult
import com.omooooori.data.GithubUserResult
import com.omooooori.model.GithubUser
import com.omooooori.model.GithubUserDetail
import com.omooooori.model.GithubUserEvent

fun GithubUserResult.toModel(): GithubUser {
    return GithubUser(
        id = id,
        username = login,
        avatarUrl = avatarUrl,
    )
}

fun GithubUserDetailResult.toModel(): GithubUserDetail {
    return GithubUserDetail(
        id = id,
        username = login,
        name = name,
        company = company,
        location = location,
        publicRepos = publicRepos,
        followers = followers,
        following = following,
    )
}

fun GithubUserEventResult.toModel(): GithubUserEvent {
    return GithubUserEvent(
        id = id,
        type = type,
        repoName = repo.name,
    )
}
