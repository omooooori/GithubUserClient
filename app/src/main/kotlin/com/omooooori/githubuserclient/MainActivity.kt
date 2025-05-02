package com.omooooori.githubuserclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.omooooori.core.theme.GithubUserClientTheme
import com.omooooori.githubuserclient.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubUserClientTheme {
                AppNavigation()
            }
        }
    }
}
