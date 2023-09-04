package com.bangkit.githubuserapplication.core.di

import com.bangkit.githubuserapplication.BuildConfig
import com.bangkit.githubuserapplication.data.source.remote.network.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", "token ${BuildConfig.API_KEY}")
                .build()
            chain.proceed(requestHeaders)
        }

        val loggingInterceptor = if(BuildConfig.DEBUG){
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient): ApiService = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(ApiService::class.java)
}