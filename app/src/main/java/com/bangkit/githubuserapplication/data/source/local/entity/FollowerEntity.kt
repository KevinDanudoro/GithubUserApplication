package com.bangkit.githubuserapplication.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("follower_table")
data class FollowerEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("username")
    val username: String,

    @ColumnInfo("avatar_url")
    val avatarUrl: String,
)