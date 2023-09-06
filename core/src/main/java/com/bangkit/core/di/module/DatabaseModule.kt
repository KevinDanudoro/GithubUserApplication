package com.bangkit.core.di.module

import android.content.Context
import androidx.room.Room
import com.bangkit.core.data.source.local.room.GithubUserDatabase
import com.bangkit.core.data.source.local.room.dao.FollowerDao
import com.bangkit.core.data.source.local.room.dao.FollowingDao
import com.bangkit.core.data.source.local.room.dao.GithubUserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(context: Context): GithubUserDatabase = Room.databaseBuilder(
        context,
        GithubUserDatabase::class.java,
        "GithubUser.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideGithubUserDao(database: GithubUserDatabase): GithubUserDao = database.githubUserDao()

    @Provides
    fun provideFollowerDao(database: GithubUserDatabase): FollowerDao = database.followerDao()

    @Provides
    fun provideFollowingDao(database: GithubUserDatabase): FollowingDao = database.followingDao()
}