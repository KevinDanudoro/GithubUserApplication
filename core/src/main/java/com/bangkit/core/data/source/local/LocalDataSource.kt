package com.bangkit.core.data.source.local

import com.bangkit.core.data.source.local.datastore.SettingPreferences
import com.bangkit.core.data.source.local.room.dao.FollowerDao
import com.bangkit.core.data.source.local.room.dao.FollowingDao
import com.bangkit.core.data.source.local.room.dao.GithubUserDao
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

    fun getAllGithubUser(): Flow<List<com.bangkit.core.data.source.local.entity.GithubUserEntity>> = githubUserDao.getAllGithubUser()

    fun getOneGithubUser(username: String): Flow<com.bangkit.core.data.source.local.entity.GithubUserEntity> =
        githubUserDao.getOneGithubUser(username)

    suspend fun insertAllGithubUser(githubUserList: List<com.bangkit.core.data.source.local.entity.GithubUserEntity>) =
        githubUserDao.insertAllGithubUser(githubUserList)

    suspend fun updateGithubUser(githubUser: com.bangkit.core.data.source.local.entity.GithubUserEntity) =
        githubUserDao.updateGithubUser(githubUser)

    suspend fun deleteFavoriteGithubUser(githubUser: com.bangkit.core.data.source.local.entity.GithubUserEntity) =
        githubUserDao.deleteOneGithubUser(githubUser)

    suspend fun deleteNonFavoriteGithubUser() = githubUserDao.deleteNonFavoriteGithubUser()


    fun getAllFavoriteGithubUser(): Flow<List<com.bangkit.core.data.source.local.entity.GithubUserEntity>> =
        githubUserDao.getAllFavoriteUser()

    suspend fun setFavoriteGithubUser(favoriteUser: com.bangkit.core.data.source.local.entity.GithubUserEntity, state: Int) {
        favoriteUser.isFavorite = state
        githubUserDao.setFavoriteUser(favoriteUser)
    }

    fun getFollowing() = followingDao.getFollowing()
    suspend fun insertFollowing(following: List<com.bangkit.core.data.source.local.entity.FollowingEntity>) = followingDao.insertFollowing(following)
    suspend fun deleteFollowing() = followingDao.deleteFollowing()

    fun getFollower() = followerDao.getFollower()
    suspend fun insertFollower(follower: List<com.bangkit.core.data.source.local.entity.FollowerEntity>) = followerDao.insertFollower(follower)
    suspend fun deleteFollower() = followerDao.deleteFollower()

    fun getThemeSetting() = datastore.getThemeSetting()
    suspend fun setThemeSetting(darkMode: Boolean) = datastore.saveThemeSetting(darkMode)
}