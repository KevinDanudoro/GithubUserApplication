package com.bangkit.githubuserapplication.domain.usecase

import com.bangkit.githubuserapplication.data.Resource
import com.bangkit.githubuserapplication.domain.model.GithubUser
import com.bangkit.githubuserapplication.domain.repository.IGithubUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubUserInteractor @Inject constructor(private val githubUserRepository: IGithubUserRepository): GithubUserUseCase {
    override fun getAllGithubUser(username: String): Flow<Resource<List<GithubUser>>> =
        githubUserRepository.getAllGithubUser(username)

    override fun getDetailGithubUser(username: String): Flow<Resource<GithubUser>> =
        githubUserRepository.getDetailGithubUser(username)

    override suspend fun deleteFavoriteGithubUser(githubUser: GithubUser) =
        githubUserRepository.deleteOneFavoriteGithubUser(githubUser)

    override fun getAllFavoriteUser(): Flow<List<GithubUser>> =
        githubUserRepository.getAllFavoriteUser()

    override suspend fun setFavoriteGithubUser(githubUser: GithubUser, state: Int) =
        githubUserRepository.setFavoriteGithubUser(githubUser, state)


    override fun getFollowers(username: String): Flow<Resource<List<GithubUser>>> =
        githubUserRepository.getFollowers(username)

    override fun getFollowings(username: String): Flow<Resource<List<GithubUser>>> =
        githubUserRepository.getFollowings(username)

    override fun getThemeSetting(): Flow<Boolean> =
        githubUserRepository.getThemeSetting()

    override suspend fun setThemeSetting(darkMode: Boolean) {
        githubUserRepository.setThemeSetting(darkMode)
    }
}