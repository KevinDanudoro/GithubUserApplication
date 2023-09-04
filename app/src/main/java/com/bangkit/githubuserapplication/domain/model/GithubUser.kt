package com.bangkit.githubuserapplication.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser(
    val name: String?,
    val username: String,
    val avatarUrl: String,
    val following: Int?,
    val followers: Int?,
    val isFavorite: Int = 0
): Parcelable
