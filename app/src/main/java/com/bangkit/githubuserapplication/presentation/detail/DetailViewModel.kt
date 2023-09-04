package com.bangkit.githubuserapplication.presentation.detail

import androidx.lifecycle.*
import com.bangkit.githubuserapplication.data.source.remote.network.ApiConfig
import com.bangkit.githubuserapplication.domain.model.GithubUser
import com.bangkit.githubuserapplication.domain.usecase.GithubUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val githubUserUseCase: GithubUserUseCase): ViewModel() {
    fun getDetailGithubUser(username: String) = githubUserUseCase.getDetailGithubUser(username).asLiveData()

    fun githubUserFollowers(username: String) =
        githubUserUseCase.getFollowers(username).asLiveData()

    fun githubUserFollowing(username: String) =
        githubUserUseCase.getFollowings(username).asLiveData()
}