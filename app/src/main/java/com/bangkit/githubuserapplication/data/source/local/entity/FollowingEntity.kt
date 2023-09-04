package com.bangkit.githubuserapplication.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("following_table")
data class FollowingEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("username")
    val username: String,

    @ColumnInfo("avatar_url")
    val avatarUrl: String,
)