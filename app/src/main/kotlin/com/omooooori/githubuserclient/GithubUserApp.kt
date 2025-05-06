package com.omooooori.githubuserclient

import android.app.Application
import org.koin.android.ext.koin.androidContext
import com.omooooori.githubuserclient.di.appModule
import org.koin.core.context.startKoin

class GithubUserClientApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GithubUserClientApp)
            modules(appModule)
        }
    }
}
