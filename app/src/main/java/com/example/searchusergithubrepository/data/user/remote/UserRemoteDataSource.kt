package com.example.searchusergithubrepository.data.user.remote

import com.example.searchusergithubrepository.data.Repo
import com.example.searchusergithubrepository.data.Result
import com.example.searchusergithubrepository.data.RetrofitFactory
import com.example.searchusergithubrepository.data.User
import com.example.searchusergithubrepository.data.mapper.usermapper.UserMapper
import com.example.searchusergithubrepository.data.repo.remote.RepoRemote
import com.example.searchusergithubrepository.data.user.UserRepositoryDataSource

class UserRemoteDataSource : UserRepositoryDataSource {
    override suspend fun search(searchWord: String, page: Int): Result<List<User>> {
        val usersRemote: MutableList<UserRemote> = mutableListOf()
        try {
            val response = RetrofitFactory.USER_API.search(searchWord, page)
            usersRemote.addAll(response.body()?.items!!)
            if (!response.isSuccessful) {
                return Result.Error(Exception("Get data users from server failed"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.Error(e)
        }

        if (usersRemote.isNotEmpty()) {
            usersRemote.addAll(listOf())
        }

        return Result.Success(
            UserMapper.usersRemoteToUsers(usersRemote)
        )
    }

    override suspend fun getUser(username: String): Result<User> {
        return try {
            val response = RetrofitFactory.USER_API.getUser(username)
            val user = UserMapper.userRemoteToUser(response.body()!!)
            Result.Success(user)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.Error(e)
        }
    }

    override suspend fun getRepos(username: String): Result<List<Repo>> {
        val repoRemote: MutableList<RepoRemote> = mutableListOf()
        try {
            val response = RetrofitFactory.USER_API.getRepos(username)
            repoRemote.addAll(response.body()!!)
            if (!response.isSuccessful) {
                return Result.Error(Exception("Get data users from server failed"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.Error(e)
        }

        if (repoRemote.isNotEmpty()) {
            repoRemote.addAll(listOf())
        }

        return Result.Success(
            UserMapper.userRemoteReposToUserRepos(repoRemote)
        )
    }


}