package com.example.searchusergithubrepository.data.user

import com.example.searchusergithubrepository.data.BaseResponse
import com.example.searchusergithubrepository.data.user.remote.UserRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("search/users?q=tom")
    suspend fun search(@Query("q") searchWord: String): Response<BaseResponse<List<UserRemote>>>
}