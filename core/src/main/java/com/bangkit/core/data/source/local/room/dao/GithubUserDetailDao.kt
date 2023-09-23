package com.bangkit.core.data.source.local.room.dao

import androidx.room.*
import com.bangkit.core.data.source.local.entity.GithubUserDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubUserDetailDao {
    @Query("SELECT * FROM github_user_detail_table WHERE username=:username LIMIT 1")
    fun getDetailGithubUser(username: String): Flow<GithubUserDetailEntity?>

    @Query("SELECT * FROM github_user_detail_table WHERE is_favorite=1 ORDER BY username ASC")
    fun getAllFavoriteGithubUser(): Flow<List<GithubUserDetailEntity>>

    @Delete(entity = GithubUserDetailEntity::class)
    suspend fun deleteFavoriteGithubUser(githubUser: GithubUserDetailEntity)

    @Query("DELETE FROM github_user_detail_table WHERE is_favorite=0")
    suspend fun deleteNonFavoriteGithubUser()

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = GithubUserDetailEntity::class)
    suspend fun insertGithubUserDetail(githubUserList: GithubUserDetailEntity)

    @Update(entity = GithubUserDetailEntity::class)
    suspend fun setFavoriteUser(favoriteUser: GithubUserDetailEntity)
}