package com.example.searchusergithubrepository.data.user

import com.example.searchusergithubrepository.data.Result
import com.example.searchusergithubrepository.data.User
import kotlinx.coroutines.flow.Flow

interface UserRepositoryContract {
    suspend fun search(searchWord: String): Flow<Result<List<User>>>
}