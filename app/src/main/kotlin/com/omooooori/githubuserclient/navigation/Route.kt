package com.omooooori.githubuserclient.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    object UserList : Screen("userList")
    object UserDetail : Screen("userDetail/{username}/{avatarUrl}") {
        fun createRoute(
            username: String,
            avatarUrl: String,
        ) = "userDetail/$username/${Uri.encode(avatarUrl)}"
    }
}
