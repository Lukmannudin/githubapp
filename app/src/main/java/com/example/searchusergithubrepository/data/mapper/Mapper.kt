package com.example.searchusergithubrepository.data.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}