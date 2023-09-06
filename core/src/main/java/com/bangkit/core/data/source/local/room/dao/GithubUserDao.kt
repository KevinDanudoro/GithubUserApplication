package com.bangkit.core.data.source.local.room.dao

import androidx.room.*
import com.bangkit.core.data.source.local.entity.GithubUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubUserDao {
    @Query("SELECT * FROM github_user_table ORDER BY username ASC")
    fun getAllGithubUser(): Flow<List<GithubUserEntity>>

    @Query("SELECT * FROM github_user_table WHERE username = :username LIMIT 1")
    fun getOneGithubUser(username: String): Flow<GithubUserEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = GithubUserEntity::class)
    suspend fun insertAllGithubUser(githubUserList: List<GithubUserEntity>)

    @Update(entity = GithubUserEntity::class)
    suspend fun updateGithubUser(githubUser: GithubUserEntity)

    @Delete(entity = GithubUserEntity::class)
    suspend fun deleteOneGithubUser(githubUser: GithubUserEntity)

    @Query("DELETE FROM github_user_table WHERE is_favorite=0")
    suspend fun deleteNonFavoriteGithubUser()


    @Query("SELECT * FROM github_user_table WHERE is_favorite=1 ORDER BY username ASC")
    fun getAllFavoriteUser(): Flow<List<GithubUserEntity>>

    @Update(entity = GithubUserEntity::class)
    suspend fun setFavoriteUser(favoriteUser: GithubUserEntity)
}