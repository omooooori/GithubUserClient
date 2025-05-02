package com.omooooori.githubuserclient.navigation

sealed class Screen(val route: String) {
    object UserList : Screen("userList")
    object UserDetail : Screen("userDetail/{userId}") {
        fun createRoute(userId: String) = "userDetail/$userId"
    }
}
