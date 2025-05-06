package com.omooooori.githubuserclient.di

import com.omooooori.api.KtorHttpClient
import com.omooooori.api.repository.GithubUserApiRepository
import com.omooooori.api.repository.GithubUserRepository
import com.omooooori.domain.FetchUserDetailUseCase
import com.omooooori.domain.FetchUserEventsUseCase
import com.omooooori.domain.FetchUsersUseCase
import com.omooooori.feature_userdetail.UserDetailViewModel
import com.omooooori.feature_userlist.UserListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { KtorHttpClient.httpClient() }

    single<GithubUserRepository> { GithubUserApiRepository(get()) }

    single { FetchUsersUseCase(get()) }
    single { FetchUserDetailUseCase(get()) }
    single { FetchUserEventsUseCase(get()) }

    viewModel { UserListViewModel(get()) }
    viewModel { UserDetailViewModel(get(), get() ) }
}
