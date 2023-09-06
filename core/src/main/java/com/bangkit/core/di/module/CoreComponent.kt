package com.bangkit.core.di.module

import android.content.Context
import com.bangkit.core.domain.repository.IGithubUserRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepositoryModule::class]
)
interface CoreComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): CoreComponent
    }

    fun provideRepository(): IGithubUserRepository
}