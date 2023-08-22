package com.bangkit.githubuserapplication.model

import androidx.lifecycle.*
import com.bangkit.githubuserapplication.core.api.ApiConfig
import com.bangkit.githubuserapplication.core.datastore.SettingPreferences
import com.bangkit.githubuserapplication.core.gson.GithubUserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {
    private val _githubUser = MutableLiveData<GithubUserResponse?>()
    val githubUser: LiveData<GithubUserResponse?> = _githubUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    val isDarkMode: LiveData<Boolean> = pref.getThemeSetting().asLiveData()

    fun getGithubUserData(searchValue: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getGithubUser(searchValue)
        client.enqueue(object : Callback<GithubUserResponse> {
            override fun onResponse(
                call: Call<GithubUserResponse>,
                response: Response<GithubUserResponse>,
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _isError.value = false
                    _githubUser.value = responseBody
                } else {
                    _isError.value = true
                }
            }

            override fun onFailure(call: Call<GithubUserResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
            }
        })
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}