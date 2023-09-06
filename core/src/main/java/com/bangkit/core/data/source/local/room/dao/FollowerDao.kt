package com.bangkit.core.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bangkit.core.data.source.local.entity.FollowerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FollowerDao {
    @Query("SELECT * FROM follower_table")
    fun getFollower(): Flow<List<FollowerEntity>>

    @Insert(entity = FollowerEntity::class)
    suspend fun insertFollower(follower: List<FollowerEntity>)

    @Query("DELETE FROM follower_table")
    suspend fun deleteFollower()
}