package com.bangkit.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
// TODO: Data Class GithubResponse dengan GithubUserEntity tidak serupa (Perlu null value / lengkapi response)
data class ListGithubUserResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<GithubUserResponse>
)

@Parcelize
data class GithubUserResponse(
	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("login")
	val username: String
) : Parcelable
