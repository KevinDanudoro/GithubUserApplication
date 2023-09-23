package com.bangkit.core.data.source.local.room.dao

import androidx.room.*
import com.bangkit.core.data.source.local.entity.GithubUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubUserDao {
    @Query("SELECT * FROM github_user_table ORDER BY username ASC")
    fun getAllGithubUser(): Flow<List<GithubUserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = GithubUserEntity::class)
    suspend fun insertAllGithubUser(githubUserList: List<GithubUserEntity>)

    @Query("DELETE FROM github_user_table")
    suspend fun deleteAllGithubUser()
}