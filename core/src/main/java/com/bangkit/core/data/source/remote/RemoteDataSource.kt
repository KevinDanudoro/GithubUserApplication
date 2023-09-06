package com.bangkit.core.data.source.remote

import android.util.Log
import com.bangkit.core.data.source.remote.network.ApiResponse
import com.bangkit.core.data.source.remote.network.ApiService
import com.bangkit.core.data.source.remote.response.DetailGithubUserResponse
import com.bangkit.core.data.source.remote.response.GithubUserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService){

    fun getAllGithubUser(username: String): Flow<ApiResponse<List<GithubUserResponse>>>{
        return flow{
            try {
                val response = apiService.getGithubUser(username)
                val data = response.items
                if(data.isNotEmpty()) emit(ApiResponse.Success(response.items))
                else emit(ApiResponse.Empty)
            } catch (e: Exception){
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getDetailGithubUser(username: String): Flow<ApiResponse<DetailGithubUserResponse>>{
        return flow{
            try {
                val response = apiService.getDetailGithubUser(username)
                val name = response.name
                if(name.isNotEmpty()) emit(ApiResponse.Success(response))
                else emit(ApiResponse.Empty)
            } catch (e: Exception){
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getGithubUserFollowers(username: String): Flow<ApiResponse<List<GithubUserResponse>>> {
        return flow{
            try {
                val response = apiService.getGithubUserFollowers(username)
                if(response.isNotEmpty()) emit(ApiResponse.Success(response))
                else emit(ApiResponse.Empty)
            } catch (e: Exception){
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getGithubUserFollowing(username: String): Flow<ApiResponse<List<GithubUserResponse>>> {
        return flow{
            try {
                val response = apiService.getGithubUserFollowing(username)
                if(response.isNotEmpty()) emit(ApiResponse.Success(response))
                else emit(ApiResponse.Empty)
            } catch (e: Exception){
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

}