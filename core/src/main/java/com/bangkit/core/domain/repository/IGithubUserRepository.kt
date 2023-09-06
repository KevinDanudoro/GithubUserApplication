package com.bangkit.core.domain.repository

import com.bangkit.core.data.Resource
import com.bangkit.core.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface IGithubUserRepository {
    fun getAllGithubUser(username: String):  Flow<Resource<List<GithubUser>>>
    fun getDetailGithubUser(username: String): Flow<Resource<GithubUser>>
    suspend fun deleteOneFavoriteGithubUser(githubUser: GithubUser)

    fun getAllFavoriteUser(): Flow<List<GithubUser>>
    suspend fun setFavoriteGithubUser(githubUser: GithubUser, state: Int)

    fun getFollowers(username: String): Flow<Resource<List<GithubUser>>>
    fun getFollowings(username: String): Flow<Resource<List<GithubUser>>>

    fun getThemeSetting(): Flow<Boolean>
    suspend fun setThemeSetting(darkMode: Boolean)
}