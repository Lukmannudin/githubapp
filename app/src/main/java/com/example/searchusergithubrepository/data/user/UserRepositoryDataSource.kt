package com.example.searchusergithubrepository.data.user

import com.example.searchusergithubrepository.data.Repo
import com.example.searchusergithubrepository.data.Result
import com.example.searchusergithubrepository.data.User

interface UserRepositoryDataSource {
    suspend fun search(searchWord: String, page: Int): Result<List<User>>

    suspend fun getUser(username: String): Result<User>

    suspend fun getRepos(username: String): Result<List<Repo>>
}