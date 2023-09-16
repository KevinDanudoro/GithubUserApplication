package com.bangkit.githubuserapplication.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.core.domain.usecase.GithubUserUseCase

class FavoriteViewModel(private val githubUserUseCase: GithubUserUseCase): ViewModel() {
    fun getAllFavoriteUser() = githubUserUseCase.getAllFavoriteUser().asLiveData()
}