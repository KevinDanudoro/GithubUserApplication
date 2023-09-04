package com.bangkit.githubuserapplication.presentation.main

import android.util.Log
import androidx.lifecycle.*
import com.bangkit.githubuserapplication.data.Resource
import com.bangkit.githubuserapplication.domain.model.GithubUser
import com.bangkit.githubuserapplication.domain.usecase.GithubUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MainViewModel(private val githubUserUseCase: GithubUserUseCase) : ViewModel() {
    private val _githubUser = MutableLiveData<Resource<List<GithubUser>>?>()
    val githubUser: LiveData<Resource<List<GithubUser>>?> = _githubUser

    fun getAllGithubUser(username: String) {
        // Jika diberi dispatcher malah error? kenapa ya?
        viewModelScope.launch {
            githubUserUseCase.getAllGithubUser(username)
                .catch { error -> Log.d("MainViewModel", error.message.toString()) }
                .collect {
                    _githubUser.value = it
                }
        }
    }

    val isDarkMode: LiveData<Boolean> = githubUserUseCase.getThemeSetting().asLiveData()

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            githubUserUseCase.setThemeSetting(isDarkModeActive)
        }
    }
}