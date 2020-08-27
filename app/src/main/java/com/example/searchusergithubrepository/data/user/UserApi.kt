package com.example.searchusergithubrepository.data.user

import com.example.searchusergithubrepository.data.BaseResponse
import com.example.searchusergithubrepository.data.user.remote.UserRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("search/users")
    suspend fun search(@Query("q") searchWord: String, @Query("page") page: Int):
            Response<BaseResponse<List<UserRemote>>>

}