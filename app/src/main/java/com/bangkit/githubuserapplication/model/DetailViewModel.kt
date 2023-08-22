package com.bangkit.githubuserapplication.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.githubuserapplication.core.api.ApiConfig
import com.bangkit.githubuserapplication.core.gson.DetailGithubUserResponse
import com.bangkit.githubuserapplication.core.gson.GithubFollowResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _username = MutableLiveData<String?>(null)
    val username: LiveData<String?> = _username

    private val _detailGithubUser = MutableLiveData<DetailGithubUserResponse?>()
    val detailGithubUser: LiveData<DetailGithubUserResponse?> = _detailGithubUser

    private val _githubUserFollowers = MutableLiveData<ArrayList<GithubFollowResponseItem>?>()
    val githubUserFollowers: LiveData<ArrayList<GithubFollowResponseItem>?> = _githubUserFollowers

    private val _githubUserFollowing = MutableLiveData<ArrayList<GithubFollowResponseItem>?>()
    val githubUserFollowing: LiveData<ArrayList<GithubFollowResponseItem>?> = _githubUserFollowing

    private val _detailLoading = MutableLiveData<Boolean>()
    val detailLoading: LiveData<Boolean> = _detailLoading

    private val _followersLoading = MutableLiveData<Boolean>()
    val followersLoading: LiveData<Boolean> = _followersLoading

    private val _followingLoading = MutableLiveData<Boolean>()
    val followingLoading: LiveData<Boolean> = _followingLoading

    private val _detailError = MutableLiveData<Boolean>()
    val detailError: LiveData<Boolean> = _detailError

    private val _followersError = MutableLiveData<Boolean>()
    val followersError: LiveData<Boolean> = _followersError

    private val _followingError = MutableLiveData<Boolean>()
    val followingError: LiveData<Boolean> = _followingError

    fun setUsername(newUsername: String){
        _username.value = newUsername
    }

    fun getDetailGithubUser(user: String){
        _detailLoading.value = true

        val client = ApiConfig.getApiService().getDetailGithubUser(user)
        client.enqueue(object: Callback<DetailGithubUserResponse> {
            override fun onResponse(
                call: Call<DetailGithubUserResponse>,
                response: Response<DetailGithubUserResponse>,
            ) {
                _detailLoading.value = false
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    _detailError.value = false
                    _detailGithubUser.value = responseBody
                } else {
                    _detailError.value = true
                }
            }

            override fun onFailure(call: Call<DetailGithubUserResponse>, t: Throwable) {
                _detailLoading.value = false
                _detailError.value = true
            }

        })
    }

    fun getGithubUserFollowers(user: String){
        _followersLoading.value = true

        val client = ApiConfig.getApiService().getGithubUserFollowers(user)
        client.enqueue(object: Callback<ArrayList<GithubFollowResponseItem>>{
            override fun onResponse(
                call: Call<ArrayList<GithubFollowResponseItem>>,
                response: Response<ArrayList<GithubFollowResponseItem>>,
            ) {
                _followersLoading.value = false
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    _followersError.value = false
                    _githubUserFollowers.value = responseBody
                } else {
                    _followersError.value = true
                }
            }

            override fun onFailure(call: Call<ArrayList<GithubFollowResponseItem>>, t: Throwable) {
                _followersLoading.value = false
                _followersError.value = true
            }
        })
    }

    fun getGithubUserFollowing(user: String){
        _followingLoading.value = true

        val client = ApiConfig.getApiService().getGithubUserFollowing(user)
        client.enqueue(object: Callback<ArrayList<GithubFollowResponseItem>>{
            override fun onResponse(
                call: Call<ArrayList<GithubFollowResponseItem>>,
                response: Response<ArrayList<GithubFollowResponseItem>>,
            ) {
                _followingLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _followingError.value = false
                    _githubUserFollowing.value = responseBody
                } else {
                    _followingError.value = true
                }
            }

            override fun onFailure(call: Call<ArrayList<GithubFollowResponseItem>>, t: Throwable) {
                _followingLoading.value = false
                _followingError.value = true
            }
        })
    }
}