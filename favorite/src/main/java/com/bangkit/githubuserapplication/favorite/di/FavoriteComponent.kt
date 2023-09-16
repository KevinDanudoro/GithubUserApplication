package com.bangkit.githubuserapplication.favorite.di

import android.content.Context
import com.bangkit.core.di.AppScope
import com.bangkit.core.di.FavoriteScope
import com.bangkit.core.di.module.AppModule
import com.bangkit.core.di.module.CoreComponent
import com.bangkit.githubuserapplication.favorite.ui.FavoriteActivity
import dagger.Component

@FavoriteScope
@Component(dependencies = [CoreComponent::class], modules = [AppModule::class])
interface FavoriteComponent {

    fun inject(activity: FavoriteActivity)

    @Component.Builder
    interface Builder {
        fun appDependencies(coreComponent: CoreComponent): Builder
        fun build(): FavoriteComponent
    }

}