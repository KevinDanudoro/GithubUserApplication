package com.bangkit.githubuserapplication.core.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM favorite_user_table ORDER BY id ASC")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM favorite_user_table WHERE username = :username")
    fun getOneFavoriteUser(username: String): LiveData<List<FavoriteUser>>

    @Insert
    suspend fun insert(favoriteUser: FavoriteUser)

    @Delete
    suspend fun delete(favoriteUser: FavoriteUser)
}