package com.bangkit.githubuserapplication.core.di

import com.bangkit.githubuserapplication.presentation.detail.DetailActivity
import com.bangkit.githubuserapplication.presentation.detail.FollowFragment
import com.bangkit.githubuserapplication.presentation.favorite.FavoriteActivity
import com.bangkit.githubuserapplication.presentation.main.MainActivity
import dagger.Component
import dagger.Module

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(favoriteActivity: FavoriteActivity)
    fun inject(detailActivity: DetailActivity)
    fun inject(followFragment: FollowFragment)
}