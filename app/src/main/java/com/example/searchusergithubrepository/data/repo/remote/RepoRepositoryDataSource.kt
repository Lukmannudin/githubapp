package com.example.searchusergithubrepository.data.repo.remote

import com.example.searchusergithubrepository.data.Result

interface RepoRepositoryDataSource {
    suspend fun fetchRepos(userName: String): Result<List<List<RepoRemote>>>
}