package com.bangkit.githubuserapplication

import android.app.Application
import android.content.Context
import com.bangkit.core.di.module.CoreComponent
import com.bangkit.core.di.module.DaggerCoreComponent
import com.bangkit.githubuserapplication.di.AppComponent
import com.bangkit.githubuserapplication.di.DaggerAppComponent
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitcompat.SplitCompatApplication

open class MyApplication: SplitCompatApplication() {
    private val coreComponent: CoreComponent by lazy{
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}