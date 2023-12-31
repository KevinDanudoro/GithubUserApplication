package com.bangkit.githubuserapplication.presentation.detail

import androidx.lifecycle.*
import com.bangkit.core.domain.model.GithubUser
import com.bangkit.core.domain.usecase.GithubUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val githubUserUseCase: GithubUserUseCase): ViewModel() {
    fun getDetailGithubUser(username: String) = githubUserUseCase.getDetailGithubUser(username).asLiveData()

    fun githubUserFollowers(username: String) =
        githubUserUseCase.getFollowers(username).asLiveData()

    fun githubUserFollowing(username: String) =
        githubUserUseCase.getFollowings(username).asLiveData()

    fun setFavoriteGithubUser(favoriteUser: GithubUser, state: Int){
        viewModelScope.launch(Dispatchers.IO) {
            githubUserUseCase.setFavoriteGithubUser(favoriteUser, state)
        }
    }
}