package com.bangkit.githubuserapplication.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.githubuserapplication.core.database.FavoriteUser
import com.bangkit.githubuserapplication.repository.FavoriteUserRepository
import kotlinx.coroutines.*

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private val mFavoriteUserRepository = FavoriteUserRepository(application)

    fun getAllFavoriteUser() = mFavoriteUserRepository.getAllFavoriteUser()

    fun getOneFavoriteUser(username: String) = mFavoriteUserRepository.getOneFavoriteUser(username)

    fun insert(favoriteUser: FavoriteUser){
        viewModelScope.launch(Dispatchers.IO) {
            mFavoriteUserRepository.insert(favoriteUser)
        }
    }

    fun delete(favoriteUser: FavoriteUser){
        viewModelScope.launch(Dispatchers.IO) {
            mFavoriteUserRepository.delete(favoriteUser)
        }
    }
}