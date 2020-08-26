package com.example.searchusergithubrepository.data.user

import com.example.searchusergithubrepository.data.Result
import com.example.searchusergithubrepository.data.User

interface UserRepositoryDataSource {
    suspend fun search(searchWord: String): Result<List<User>>
}