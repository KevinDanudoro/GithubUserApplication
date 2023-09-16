package com.bangkit.githubuserapplication.di

import com.bangkit.core.di.module.AppModule
import com.bangkit.core.di.AppScope
import com.bangkit.core.di.module.CoreComponent
import com.bangkit.githubuserapplication.di.module.ViewModelModule
import com.bangkit.githubuserapplication.presentation.detail.DetailActivity
import com.bangkit.githubuserapplication.presentation.detail.FollowFragment
import com.bangkit.githubuserapplication.presentation.main.MainActivity
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class, ViewModelModule::class]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(detailActivity: DetailActivity)
    fun inject(followFragment: FollowFragment)
}