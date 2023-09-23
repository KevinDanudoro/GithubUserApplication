package com.bangkit.core.data.source.local

import com.bangkit.core.data.source.local.datastore.SettingPreferences
import com.bangkit.core.data.source.local.entity.FollowerEntity
import com.bangkit.core.data.source.local.entity.FollowingEntity
import com.bangkit.core.data.source.local.entity.GithubUserDetailEntity
import com.bangkit.core.data.source.local.entity.GithubUserEntity
import com.bangkit.core.data.source.local.room.dao.FollowerDao
import com.bangkit.core.data.source.local.room.dao.FollowingDao
import com.bangkit.core.data.source.local.room.dao.GithubUserDao
import com.bangkit.core.data.source.local.room.dao.GithubUserDetailDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val githubUserDao: GithubUserDao,
    private val githubUserDetailDao: GithubUserDetailDao,
    private val followingDao: FollowingDao,
    private val followerDao: FollowerDao,
    private val datastore: SettingPreferences
) {

    fun getAllGithubUser(): Flow<List<GithubUserEntity>> =
        githubUserDao.getAllGithubUser()

    suspend fun insertAllGithubUser(githubUserList: List<GithubUserEntity>) =
        githubUserDao.insertAllGithubUser(githubUserList)

    suspend fun deleteAllGithubUser() = githubUserDao.deleteAllGithubUser()

    fun getDetailGithubUser(username: String): Flow<GithubUserDetailEntity?> =
        githubUserDetailDao.getDetailGithubUser(username)

    fun getAllFavoriteGithubUser(): Flow<List<GithubUserDetailEntity>> =
        githubUserDetailDao.getAllFavoriteGithubUser()

    suspend fun deleteFavoriteGithubUser(githubUser: GithubUserDetailEntity) =
        githubUserDetailDao.deleteFavoriteGithubUser(githubUser)

    suspend fun deleteNonFavoriteGithubUser() =
        githubUserDetailDao.deleteNonFavoriteGithubUser()

    suspend fun insertGithubUserDetail(githubUser: GithubUserDetailEntity) =
        githubUserDetailDao.insertGithubUserDetail(githubUser)

    suspend fun setFavoriteGithubUser(favoriteUser: GithubUserDetailEntity) =
        githubUserDetailDao.setFavoriteUser(favoriteUser)

    fun getFollowing() =
        followingDao.getFollowing()
    suspend fun insertFollowing(following: List<FollowingEntity>) =
        followingDao.insertFollowing(following)
    suspend fun deleteFollowing() =
        followingDao.deleteFollowing()

    fun getFollower() =
        followerDao.getFollower()
    suspend fun insertFollower(follower: List<FollowerEntity>) =
        followerDao.insertFollower(follower)
    suspend fun deleteFollower() =
        followerDao.deleteFollower()

    fun getThemeSetting() =
        datastore.getThemeSetting()
    suspend fun setThemeSetting(darkMode: Boolean) =
        datastore.saveThemeSetting(darkMode)
}