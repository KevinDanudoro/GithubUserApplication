package com.bangkit.githubuserapplication.core.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.githubuserapplication.core.di.AppScope
import com.bangkit.githubuserapplication.data.source.local.datastore.SettingPreferences
import com.bangkit.githubuserapplication.domain.usecase.GithubUserUseCase
import com.bangkit.githubuserapplication.presentation.detail.DetailViewModel
import com.bangkit.githubuserapplication.presentation.favorite.FavoriteViewModel
import com.bangkit.githubuserapplication.presentation.main.MainViewModel
import javax.inject.Inject

@AppScope
class ViewModelFactory @Inject constructor(
    private val githubUserUseCase: GithubUserUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(githubUserUseCase) as T
        } else if(modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(githubUserUseCase) as T
        } else if(modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(githubUserUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}