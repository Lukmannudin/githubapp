package com.example.searchusergithubrepository.data.user

import com.example.searchusergithubrepository.data.Result
import com.example.searchusergithubrepository.data.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository(
    private val userRemoteDataSource: UserRepositoryDataSource,
    private val userLocalDataSource: UserRepositoryDataSource
) : UserRepositoryContract {

    override suspend fun search(searchWord: String, page: Int): Flow<Result<List<User>>> = flow {
        emit(Result.Loading)
        when (val resultRemote = userRemoteDataSource.search(searchWord, page)) {
            is Result.Success -> {
                emit(Result.Success(resultRemote.data))
            }
            is Result.Error -> {
                emit(Result.Error(resultRemote.exception))
            }
        }
    }

    override suspend fun fetchUser(username: String): Flow<Result<User>> = flow {
        emit(Result.Loading)
        when (val resultRemote = userRemoteDataSource.getUser(username)) {
            is Result.Success -> {
                emit(Result.Success(resultRemote.data))
            }
            is Result.Error -> {
                emit(Result.Error(resultRemote.exception))
            }
        }
    }
}