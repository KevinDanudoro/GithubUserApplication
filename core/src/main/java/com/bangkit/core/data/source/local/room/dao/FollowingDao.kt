package com.bangkit.core.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bangkit.core.data.source.local.entity.FollowingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FollowingDao {
    @Query("SELECT * FROM following_table")
    fun getFollowing(): Flow<List<FollowingEntity>>

    @Insert(entity = FollowingEntity::class)
    suspend fun insertFollowing(following: List<FollowingEntity>)

    @Query("DELETE FROM following_table")
    suspend fun deleteFollowing()
}