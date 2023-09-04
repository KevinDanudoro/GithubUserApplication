package com.bangkit.githubuserapplication.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("github_user_table")
data class GithubUserEntity(
    @ColumnInfo("name")
    val name: String?,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("username")
    val username: String,

    @ColumnInfo("avatar_url")
    val avatarUrl: String,

    @ColumnInfo("following")
    val following: Int?,

    @ColumnInfo("followers")
    val followers: Int?,

    @ColumnInfo("is_favorite")
    var isFavorite: Int = 0
)