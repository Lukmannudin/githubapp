package com.example.searchusergithubrepository.data.mapper.usermapper

import com.example.searchusergithubrepository.data.User
import com.example.searchusergithubrepository.data.mapper.Mapper
import com.example.searchusergithubrepository.data.mapper.NullableInputListMapper
import com.example.searchusergithubrepository.data.user.remote.UserRemote

class UsersRemoteToUsers(
    private val mapper: Mapper<UserRemote, User>
) : NullableInputListMapper<UserRemote, User> {
    override fun map(input: List<UserRemote>?): List<User> {
        return input?.map {
            mapper.map(it)
        }.orEmpty()
    }
}