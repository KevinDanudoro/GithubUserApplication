package com.bangkit.githubuserapplication

import android.app.Application
import com.bangkit.githubuserapplication.core.di.AppComponent
import com.bangkit.githubuserapplication.core.di.CoreComponent
import com.bangkit.githubuserapplication.core.di.DaggerAppComponent
import com.bangkit.githubuserapplication.core.di.DaggerCoreComponent

open class MyApplication: Application() {
    private val coreComponent: CoreComponent by lazy{
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}