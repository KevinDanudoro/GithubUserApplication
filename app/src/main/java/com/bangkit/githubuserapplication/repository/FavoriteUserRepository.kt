package com.bangkit.githubuserapplication.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.bangkit.githubuserapplication.core.database.FavoriteUser
import com.bangkit.githubuserapplication.core.database.FavoriteUserDao
import com.bangkit.githubuserapplication.core.database.FavoriteUserRoomDatabase

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllFavoriteUser()

    fun getOneFavoriteUser(username: String) = mFavoriteUserDao.getOneFavoriteUser(username)

    suspend fun insert(favoriteUser: FavoriteUser) {
        mFavoriteUserDao.insert(favoriteUser)
    }

    suspend fun delete(favoriteUser: FavoriteUser) {
        mFavoriteUserDao.delete(favoriteUser)
    }
}