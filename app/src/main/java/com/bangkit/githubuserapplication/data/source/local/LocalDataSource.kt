package com.bangkit.githubuserapplication.data.source.local

import com.bangkit.githubuserapplication.data.source.local.datastore.SettingPreferences
import com.bangkit.githubuserapplication.data.source.local.entity.FollowerEntity
import com.bangkit.githubuserapplication.data.source.local.entity.FollowingEntity
import com.bangkit.githubuserapplication.data.source.local.entity.GithubUserEntity
import com.bangkit.githubuserapplication.data.source.local.room.dao.FollowerDao
import com.bangkit.githubuserapplication.data.source.local.room.dao.FollowingDao
import com.bangkit.githubuserapplication.data.source.local.room.dao.GithubUserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val githubUserDao: GithubUserDao,
    private val followingDao: FollowingDao,
    private val followerDao: FollowerDao,
    private val datastore: SettingPreferences
) {

    fun getAllGithubUser(): Flow<List<GithubUserEntity>> = githubUserDao.getAllGithubUser()

    fun getOneGithubUser(username: String): Flow<GithubUserEntity> =
        githubUserDao.getOneGithubUser(username)

    suspend fun insertAllGithubUser(githubUserList: List<GithubUserEntity>) =
        githubUserDao.insertAllGithubUser(githubUserList)

    suspend fun updateGithubUser(githubUser: GithubUserEntity) =
        githubUserDao.updateGithubUser(githubUser)

    suspend fun deleteFavoriteGithubUser(githubUser: GithubUserEntity) =
        githubUserDao.deleteOneGithubUser(githubUser)

    suspend fun deleteNonFavoriteGithubUser() = githubUserDao.deleteNonFavoriteGithubUser()


    fun getAllFavoriteGithubUser(): Flow<List<GithubUserEntity>> =
        githubUserDao.getAllFavoriteUser()

    suspend fun setFavoriteGithubUser(favoriteUser: GithubUserEntity, state: Int) {
        favoriteUser.isFavorite = state
        githubUserDao.setFavoriteUser(favoriteUser)
    }

    fun getFollowing() = followingDao.getFollowing()
    suspend fun insertFollowing(following: List<FollowingEntity>) = followingDao.insertFollowing(following)
    suspend fun deleteFollowing() = followingDao.deleteFollowing()

    fun getFollower() = followerDao.getFollower()
    suspend fun insertFollower(follower: List<FollowerEntity>) = followerDao.insertFollower(follower)
    suspend fun deleteFollower() = followerDao.deleteFollower()

    fun getThemeSetting() = datastore.getThemeSetting()
    suspend fun setThemeSetting(darkMode: Boolean) = datastore.saveThemeSetting(darkMode)
}