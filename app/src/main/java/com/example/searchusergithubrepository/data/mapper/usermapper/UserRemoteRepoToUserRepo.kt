package com.example.searchusergithubrepository.data.mapper.usermapper

import com.example.searchusergithubrepository.data.Repo
import com.example.searchusergithubrepository.data.mapper.Mapper
import com.example.searchusergithubrepository.data.repo.remote.RepoRemote

class UserRemoteRepoToUserRepo : Mapper<RepoRemote, Repo> {
    override fun map(input: RepoRemote): Repo {
        val language = input.language ?: ""
        val description = input.description ?: ""
        return Repo(
            input.stargazersCount!!,
            language,
            input.subscribersUrl!!,
            input.releasesUrl!!,
            input.svnUrl!!,
            input.id!!,
            input.name!!,
            input.jsonMemberPrivate!!,
            description,
            input.createdAt!!,
            input.fullName!!
        )
    }

}