package com.bangkit.githubuserapplication

import android.app.Application
import com.bangkit.core.di.module.CoreComponent
import com.bangkit.core.di.module.DaggerCoreComponent
import com.bangkit.githubuserapplication.di.AppComponent
import com.bangkit.githubuserapplication.di.DaggerAppComponent

open class MyApplication: Application() {
    private val coreComponent: CoreComponent by lazy{
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}