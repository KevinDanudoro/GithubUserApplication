package com.bangkit.githubuserapplication.core.di

import com.bangkit.githubuserapplication.data.GithubUserRepository
import com.bangkit.githubuserapplication.domain.repository.IGithubUserRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class, DatabaseModule::class, DatastoreModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(githubUserRepository: GithubUserRepository): IGithubUserRepository
}