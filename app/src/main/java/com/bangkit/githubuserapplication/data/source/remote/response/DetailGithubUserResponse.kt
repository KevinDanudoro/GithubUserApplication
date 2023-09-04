package com.bangkit.githubuserapplication.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailGithubUserResponse(

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("login")
	val username: String,
)
