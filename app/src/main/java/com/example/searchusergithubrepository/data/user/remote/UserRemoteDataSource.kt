package com.example.searchusergithubrepository.data.user.remote

import com.example.searchusergithubrepository.data.Result
import com.example.searchusergithubrepository.data.RetrofitFactory
import com.example.searchusergithubrepository.data.User
import com.example.searchusergithubrepository.data.user.UserRepositoryDataSource

class UserRemoteDataSource : UserRepositoryDataSource {
    override suspend fun search(searchWord: String): Result<List<User>> {
        val usersRemote: MutableList<UserRemote> = mutableListOf()
        try {

            val response = RetrofitFactory.USER_API.search(searchWord)
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

    private fun mapToUsers(userRemote: List<UserRemote>): List<User> {
        val users = mutableListOf<User>()
        userRemote.forEach {
            users.add(
                User(
                    it.gistsUrl,
                    it.reposUrl,
                    it.followingUrl,
                    it.starredUrl,
                    it.login,
                    it.followersUrl,
                    it.type,
                    it.followersUrl,
                    it.subscriptionsUrl,
                    it.score,
                    it.receivedEventsUrl,
                    it.avatarUrl,
                    it.receivedEventsUrl,
                    it.htmlUrl,
                    it.siteAdmin,
                    it.id,
                    it.gravatarId,
                    it.nodeId,
                    it.organizationsUrl
                )
            )
        }
        return users
    }
}