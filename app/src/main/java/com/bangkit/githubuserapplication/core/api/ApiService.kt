package com.bangkit.githubuserapplication.core.api

import com.bangkit.githubuserapplication.core.gson.DetailGithubUserResponse
import com.bangkit.githubuserapplication.core.gson.GithubFollowResponseItem
import com.bangkit.githubuserapplication.core.gson.GithubUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getGithubUser(
        @Query("q") q: String,
    ): Call<GithubUserResponse>

    @GET("users/{username}")
    fun getDetailGithubUser(
        @Path("username") username: String
    ) : Call<DetailGithubUserResponse>

    @GET("users/{username}/followers")
    fun getGithubUserFollowers(
        @Path("username") username: String
    ) : Call<ArrayList<GithubFollowResponseItem>>

    @GET("users/{username}/following")
    fun getGithubUserFollowing(
        @Path("username") username: String
    ) : Call<ArrayList<GithubFollowResponseItem>>
}