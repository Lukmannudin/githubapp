package com.example.searchusergithubrepository.data.mapper.usermapper

import com.example.searchusergithubrepository.data.Repo
import com.example.searchusergithubrepository.data.User
import com.example.searchusergithubrepository.data.repo.remote.RepoRemote
import com.example.searchusergithubrepository.data.user.remote.UserRemote

object UserMapper {

    fun userRemoteToUser(userRemote: UserRemote): User {
        return UserRemoteToUser().map(userRemote)
    }

    fun usersRemoteToUsers(usersRemote: List<UserRemote>): List<User> {
        return UsersRemoteToUsers(UserRemoteToUser()).map(usersRemote)
    }

    fun userRemoteReposToUserRepos(repoRemote: List<RepoRemote>): List<Repo> {
        return UserRemoteReposToUserRepos(UserRemoteRepoToUserRepo()).map(repoRemote)
    }
}