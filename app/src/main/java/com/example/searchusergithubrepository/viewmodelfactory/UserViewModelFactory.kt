package com.example.searchusergithubrepository.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.searchusergithubrepository.data.user.UserRepository
import com.example.searchusergithubrepository.data.user.remote.UserRemoteDataSource
import com.example.searchusergithubrepository.main.MainViewModel

class UserViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) -> MainViewModel(
                    userRepository = UserRepository(
                        userRemoteDataSource = UserRemoteDataSource(),
                        userLocalDataSource = UserRemoteDataSource()
                    )
                )
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
            }
        } as T
}