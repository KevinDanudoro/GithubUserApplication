package com.bangkit.githubuserapplication.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.core.domain.model.GithubUser
import com.bangkit.core.domain.usecase.GithubUserUseCase
import kotlinx.coroutines.*
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val githubUserUseCase: GithubUserUseCase): ViewModel() {
    fun getAllFavoriteUser() = githubUserUseCase.getAllFavoriteUser().asLiveData()

    fun setFavoriteGithubUser(favoriteUser: GithubUser, state: Int){
        viewModelScope.launch(Dispatchers.IO) {
            githubUserUseCase.setFavoriteGithubUser(favoriteUser, state)
        }
    }
}