package com.example.searchusergithubrepository.data.user.remote

import com.example.searchusergithubrepository.data.Result
import com.example.searchusergithubrepository.data.RetrofitFactory
import com.example.searchusergithubrepository.data.User
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
            mapToUsers(usersRemote)
        )
    }

    override suspend fun getUser(username: String): Result<User> {
        return try {
            val response = RetrofitFactory.USER_API.getUser(username)
            val user = mapToUser(response.body()!!)

            Result.Success(user)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.Error(e)
        }
    }

    private fun mapToUser(userRemote: UserRemote): User {
        val following = userRemote.following ?: -1
        val followers = userRemote.followers ?: -1

        return User(
            userRemote.gistsUrl!!,
            userRemote.reposUrl!!,
            userRemote.followingUrl!!,
            userRemote.starredUrl!!,
            userRemote.login!!,
            userRemote.followersUrl!!,
            userRemote.type!!,
            userRemote.url!!,
            userRemote.subscriptionsUrl!!,
            -1.0,
            userRemote.receivedEventsUrl!!,
            userRemote.avatarUrl!!,
            userRemote.eventsUrl!!,
            userRemote.htmlUrl!!,
            userRemote.siteAdmin!!,
            userRemote.id!!,
            userRemote.gravatarId!!,
            userRemote.nodeId!!,
            userRemote.organizationsUrl!!,
            following,
            followers
        )
    }

    private fun mapToUsers(userRemote: List<UserRemote>): List<User> {
        val users = mutableListOf<User>()
        userRemote.forEach {
            users.add(
                mapToUser(it)
            )
        }
        return users
    }
}