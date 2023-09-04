package com.bangkit.githubuserapplication.core.di

import com.bangkit.githubuserapplication.domain.usecase.GithubUserInteractor
import com.bangkit.githubuserapplication.domain.usecase.GithubUserUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun provideGithubUserUseCase(githubUserInteractor: GithubUserInteractor): GithubUserUseCase
}