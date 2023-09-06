package com.bangkit.core.di.module

import com.bangkit.core.domain.usecase.GithubUserInteractor
import com.bangkit.core.domain.usecase.GithubUserUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun provideGithubUserUseCase(githubUserInteractor: GithubUserInteractor): GithubUserUseCase
}