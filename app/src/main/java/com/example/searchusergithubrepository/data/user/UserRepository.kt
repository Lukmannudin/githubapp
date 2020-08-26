package com.example.searchusergithubrepository.data.user

import com.example.searchusergithubrepository.data.Result
import com.example.searchusergithubrepository.data.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository(
    private val userRemoteDataSource: UserRepositoryDataSource,
    private val userLocalDataSource: UserRepositoryDataSource
) : UserRepositoryContract {

    override suspend fun search(searchWord: String): Flow<Result<List<User>>> = flow {
        emit(Result.Loading)
        when (val resultRemote = userRemoteDataSource.search(searchWord)) {
            is Result.Success -> {
                emit(Result.Success(resultRemote.data))
            }
            is Result.Error -> {
                emit(Result.Error(resultRemote.exception))
            }
        }
    }
}