package com.example.searchusergithubrepository.data.repo

import com.example.searchusergithubrepository.data.Result
import com.example.searchusergithubrepository.data.repo.remote.RepoRemote
import kotlinx.coroutines.flow.Flow

interface RepoRepositoryContract {
    suspend fun fetchRepos(username: String): Flow<Result<List<RepoRemote>>>
}