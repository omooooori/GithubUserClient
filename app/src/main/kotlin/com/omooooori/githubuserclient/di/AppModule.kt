package com.omooooori.githubuserclient.di

import com.omooooori.api.KtorApiClient
import com.omooooori.api.repository.GithubUserApiRepository
import com.omooooori.api.repository.GithubUserRepository
import com.omooooori.domain.FetchUserDetailUseCase
import com.omooooori.domain.FetchUserEventsUseCase
import com.omooooori.domain.FetchUsersUseCase
import com.omooooori.feature.userdetail.UserDetailViewModel
import com.omooooori.feature.userlist.UserListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule =
    module {
        single { KtorApiClient.httpClient() }

        single<GithubUserRepository> { GithubUserApiRepository(get()) }

        single { FetchUsersUseCase(get()) }
        single { FetchUserDetailUseCase(get()) }
        single { FetchUserEventsUseCase(get()) }

        viewModel { UserListViewModel(get()) }
        viewModel { UserDetailViewModel(get(), get()) }
    }
