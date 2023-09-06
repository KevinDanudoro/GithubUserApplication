package com.bangkit.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bangkit.core.data.source.local.entity.FollowerEntity
import com.bangkit.core.data.source.local.entity.FollowingEntity
import com.bangkit.core.data.source.local.entity.GithubUserEntity
import com.bangkit.core.data.source.local.room.dao.FollowerDao
import com.bangkit.core.data.source.local.room.dao.FollowingDao
import com.bangkit.core.data.source.local.room.dao.GithubUserDao

@Database(entities = [GithubUserEntity::class, FollowerEntity::class, FollowingEntity::class], version = 1, exportSchema = false)
abstract class GithubUserDatabase : RoomDatabase() {
    abstract fun githubUserDao(): GithubUserDao
    abstract fun followerDao(): FollowerDao
    abstract fun followingDao(): FollowingDao
}