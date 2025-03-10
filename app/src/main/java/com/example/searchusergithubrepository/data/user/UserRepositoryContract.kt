package com.example.searchusergithubrepository.data.user

import com.example.searchusergithubrepository.data.Repo
import com.example.searchusergithubrepository.data.Result
import com.example.searchusergithubrepository.data.User
import kotlinx.coroutines.flow.Flow

interface UserRepositoryContract {
    suspend fun search(searchWord: String, page: Int): Flow<Result<List<User>>>

    suspend fun fetchUser(username: String): Flow<Result<User>>

    suspend fun fetchRepos(username: String): Flow<Result<List<Repo>>>

}