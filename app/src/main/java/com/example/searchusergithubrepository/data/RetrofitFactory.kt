package com.example.searchusergithubrepository.data

import com.example.searchusergithubrepository.BuildConfig
import com.example.searchusergithubrepository.data.user.UserApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {
    private val mLoggingInterceptor = HttpLoggingInterceptor()
    private val client: OkHttpClient =
        OkHttpClient.Builder().addInterceptor(mLoggingInterceptor).build()

    init {
        mLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory((CoroutineCallAdapterFactory()))
        .build()

    val USER_API: UserApi = retrofit.create(UserApi::class.java)
}