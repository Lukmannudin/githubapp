package com.example.searchusergithubrepository.data.repo

import com.example.searchusergithubrepository.data.Result
import com.example.searchusergithubrepository.data.repo.remote.RepoRemote
import com.example.searchusergithubrepository.data.repo.remote.RepoRepositoryDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepoRepository(
    private val repoRemoteDataSource: RepoRepositoryDataSource,
) : RepoRepositoryContract {

    override suspend fun fetchRepos(username: String): Flow<Result<List<RepoRemote>>> = flow {

    }
}