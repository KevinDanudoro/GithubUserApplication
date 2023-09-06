package com.bangkit.core.di.module

import com.bangkit.core.data.GithubUserRepository
import com.bangkit.core.domain.repository.IGithubUserRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class, DatabaseModule::class, DatastoreModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(githubUserRepository: GithubUserRepository): IGithubUserRepository
}