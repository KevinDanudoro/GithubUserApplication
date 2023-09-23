package com.bangkit.core.data

import com.bangkit.core.data.source.local.LocalDataSource
import com.bangkit.core.data.source.local.entity.GithubUserDetailEntity
import com.bangkit.core.data.source.remote.RemoteDataSource
import com.bangkit.core.data.source.remote.network.ApiResponse
import com.bangkit.core.data.source.remote.response.DetailGithubUserResponse
import com.bangkit.core.data.source.remote.response.GithubUserResponse
import com.bangkit.core.domain.model.GithubUser
import com.bangkit.core.domain.repository.IGithubUserRepository
import com.bangkit.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubUserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
): IGithubUserRepository {

    override fun getAllGithubUser(username: String): Flow<Resource<List<GithubUser>>> =
        object : NetworkBoundResource<List<GithubUser>, List<GithubUserResponse>>() {
            override fun loadFromDB(): Flow<List<GithubUser>> {
                return localDataSource.getAllGithubUser().map {
                    DataMapper.mapGithubEntitiesToDomain(it)
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<GithubUserResponse>>> =
                remoteDataSource.getAllGithubUser(username)

            override suspend fun saveCallResult(data: List<GithubUserResponse>) {
                val githubUserList = DataMapper.mapGithubResponseToEntities(data)
                localDataSource.deleteAllGithubUser()
                localDataSource.insertAllGithubUser(githubUserList)
            }

            override fun shouldFetch(data: List<GithubUser>?) = true

        }.asFlow()

    override fun getDetailGithubUser(username: String): Flow<Resource<GithubUser>> =
        object : NetworkBoundResource<GithubUser, DetailGithubUserResponse>(){
            override fun loadFromDB(): Flow<GithubUser> {
                return localDataSource.getDetailGithubUser(username).map {
                    DataMapper.mapGithubDetailEntitiesToDomain(it)
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<DetailGithubUserResponse>> {
                return remoteDataSource.getDetailGithubUser(username)
            }

            override suspend fun saveCallResult(data: DetailGithubUserResponse) {
                val githubUserDetail = DataMapper.mapGithubDetailResponseToEntities(data)
                localDataSource.deleteNonFavoriteGithubUser()
                localDataSource.insertGithubUserDetail(githubUserDetail)
            }

            override fun shouldFetch(data: GithubUser?) = data?.name==null
        }.asFlow()

    override fun getAllFavoriteUser(): Flow<List<GithubUser>> =
        localDataSource.getAllFavoriteGithubUser().map {
            DataMapper.mapGithubDetailEntitiesToDomain(it)
        }

    override suspend fun setFavoriteGithubUser(githubUser: GithubUser, state: Int) {
        val githubUserEntity: GithubUserDetailEntity = DataMapper
            .mapDomainToGithubDetailEntity(githubUser)
        githubUserEntity.isFavorite = state
        localDataSource.setFavoriteGithubUser(githubUserEntity)
    }

    override suspend fun deleteOneFavoriteGithubUser(githubUser: GithubUser) {
        val githubUserEntity = DataMapper.mapDomainToGithubDetailEntity(githubUser)
        localDataSource.deleteFavoriteGithubUser(githubUserEntity)
    }

    override fun getFollowers(username: String): Flow<Resource<List<GithubUser>>> {
         return object : NetworkBoundResource<List<GithubUser>, List<GithubUserResponse>>(){
            override fun loadFromDB(): Flow<List<GithubUser>> =
                localDataSource.getFollower().map {
                    DataMapper.mapFollowerEntitiesToDomain(it)
                }

            override suspend fun createCall(): Flow<ApiResponse<List<GithubUserResponse>>> {
                localDataSource.deleteFollower()
                return remoteDataSource.getGithubUserFollowers(username)
            }

            override suspend fun saveCallResult(data: List<GithubUserResponse>) {
                val followerEntity = DataMapper.mapResponsesToFollowerEntities(data)
                localDataSource.insertFollower(followerEntity)
            }

            override fun shouldFetch(data: List<GithubUser>?) = true
        }.asFlow()
    }

    override fun getFollowings(username: String): Flow<Resource<List<GithubUser>>> {
        return object : NetworkBoundResource<List<GithubUser>, List<GithubUserResponse>>(){
            override fun loadFromDB(): Flow<List<GithubUser>> =
                localDataSource.getFollowing().map {
                    DataMapper.mapFollowingEntitiesToDomain(it)
                }

            override suspend fun createCall(): Flow<ApiResponse<List<GithubUserResponse>>> {
                localDataSource.deleteFollowing()
                return remoteDataSource.getGithubUserFollowing(username)
            }

            override suspend fun saveCallResult(data: List<GithubUserResponse>) {
                val followingEntity = DataMapper.mapResponsesToFollowingEntities(data)
                localDataSource.insertFollowing(followingEntity)
            }

            override fun shouldFetch(data: List<GithubUser>?) = true

        }.asFlow()
    }

    override fun getThemeSetting(): Flow<Boolean> =
        localDataSource.getThemeSetting()

    override suspend fun setThemeSetting(darkMode: Boolean) {
        localDataSource.setThemeSetting(darkMode)
    }
}