package com.example.searchusergithubrepository.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.searchusergithubrepository.data.user.UserRepository
import com.example.searchusergithubrepository.data.user.remote.UserRemoteDataSource
import com.example.searchusergithubrepository.main.MainViewModel
import com.example.searchusergithubrepository.main.userdetail.UserDetailViewModel

class UserViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) -> MainViewModel(
                    userRepository = UserRepository(
                        userRemoteDataSource = UserRemoteDataSource(),
                        //temporary access to remote for local access
                        userLocalDataSource = UserRemoteDataSource()
                    )
                )

                isAssignableFrom(UserDetailViewModel::class.java) -> UserDetailViewModel(
                    userRepository = UserRepository(
                        userRemoteDataSource = UserRemoteDataSource(),
                        //temporary access to remote for local access
                        userLocalDataSource = UserRemoteDataSource()
                    )
                )
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
            }
        } as T
}