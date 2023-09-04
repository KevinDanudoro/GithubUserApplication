package com.bangkit.githubuserapplication.domain.usecase

import com.bangkit.githubuserapplication.data.Resource
import com.bangkit.githubuserapplication.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface GithubUserUseCase {
    fun getAllGithubUser(username: String):  Flow<Resource<List<GithubUser>>>
    fun getDetailGithubUser(username: String): Flow<Resource<GithubUser>>

    fun getAllFavoriteUser(): Flow<List<GithubUser>>
    suspend fun setFavoriteGithubUser(githubUser: GithubUser, state: Int)
    suspend fun deleteFavoriteGithubUser(githubUser: GithubUser)

    fun getFollowers(username: String): Flow<Resource<List<GithubUser>>>
    fun getFollowings(username: String): Flow<Resource<List<GithubUser>>>

    fun getThemeSetting(): Flow<Boolean>
    suspend fun setThemeSetting(darkMode: Boolean)
}