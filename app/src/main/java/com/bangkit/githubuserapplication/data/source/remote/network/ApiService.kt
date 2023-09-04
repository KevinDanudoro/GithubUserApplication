package com.bangkit.githubuserapplication.data.source.remote.network

import com.bangkit.githubuserapplication.data.source.remote.response.DetailGithubUserResponse
import com.bangkit.githubuserapplication.data.source.remote.response.GithubUserResponse
import com.bangkit.githubuserapplication.data.source.remote.response.ListGithubUserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun getGithubUser(
        @Query("q") q: String,
    ): ListGithubUserResponse

    @GET("users/{username}")
    suspend fun getDetailGithubUser(
        @Path("username") username: String
    ) : DetailGithubUserResponse

    @GET("users/{username}/followers")
    suspend fun getGithubUserFollowers(
        @Path("username") username: String
    ) : ArrayList<GithubUserResponse>

    @GET("users/{username}/following")
    suspend fun getGithubUserFollowing(
        @Path("username") username: String
    ) : ArrayList<GithubUserResponse>
}