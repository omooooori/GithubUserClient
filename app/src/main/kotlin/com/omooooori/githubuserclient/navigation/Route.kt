package com.omooooori.githubuserclient.navigation

import android.net.Uri

sealed class Route(val route: String) {
    object UserList : Route("userList")

    object UserDetail : Route("userDetail/{username}/{avatarUrl}") {
        fun createRoute(
            username: String,
            avatarUrl: String,
        ) = "userDetail/$username/${Uri.encode(avatarUrl)}"
    }
}
