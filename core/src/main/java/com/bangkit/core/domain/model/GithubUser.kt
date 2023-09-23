package com.bangkit.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser(
    val name: String?,
    val username: String,
    val avatarUrl: String,
    val following: Int? = 0,
    val followers: Int? = 0,
    val isFavorite: Int = 0
): Parcelable
