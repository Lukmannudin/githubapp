package com.example.searchusergithubrepository.data.mapper.usermapper

import com.example.searchusergithubrepository.data.Repo
import com.example.searchusergithubrepository.data.mapper.Mapper
import com.example.searchusergithubrepository.data.mapper.NullableInputListMapper
import com.example.searchusergithubrepository.data.repo.remote.RepoRemote

class UserRemoteReposToUserRepos(
    private val mapper: Mapper<RepoRemote, Repo>
) : NullableInputListMapper<RepoRemote, Repo> {
    override fun map(input: List<RepoRemote>?): List<Repo> {
        return input?.map {
            mapper.map(it)
        }.orEmpty()
    }
}