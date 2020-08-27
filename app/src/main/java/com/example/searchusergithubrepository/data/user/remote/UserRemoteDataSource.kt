package com.example.searchusergithubrepository.data.user.remote

import com.example.searchusergithubrepository.data.Repo
import com.example.searchusergithubrepository.data.Result
import com.example.searchusergithubrepository.data.RetrofitFactory
import com.example.searchusergithubrepository.data.User
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
            mapToRepos(repoRemote)
        )
    }


    private fun mapToRepos(reposRemote: List<RepoRemote>):List<Repo>{
        val repos = mutableListOf<Repo>()
        reposRemote.forEach {
            repos.add(
                mapToRepo(it)
            )
        }
        return repos
    }

    private fun mapToRepo(repoRemote: RepoRemote): Repo {
        val language = repoRemote.language ?: ""
        val description = repoRemote.description ?: ""
        return Repo(
            repoRemote.stargazersCount!!,
            language,
            repoRemote.subscribersUrl!!,
            repoRemote.releasesUrl!!,
            repoRemote.svnUrl!!,
            repoRemote.id!!,
            repoRemote.name!!,
            repoRemote.jsonMemberPrivate!!,
            description,
            repoRemote.createdAt!!,
            repoRemote.fullName!!
        )
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