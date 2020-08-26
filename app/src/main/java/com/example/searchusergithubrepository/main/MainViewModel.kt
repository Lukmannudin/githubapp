package com.example.searchusergithubrepository.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchusergithubrepository.data.Result
import com.example.searchusergithubrepository.data.User
import com.example.searchusergithubrepository.data.user.UserRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    private val userRepository: UserRepository
) : ViewModel(){

    private val _users =  MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun search(searchWord: String){
        _loading.value = true

        viewModelScope.launch {
            val users = userRepository.search(searchWord)
            users.collect {tipsResponse ->
                when (tipsResponse){
                    is Result.Loading -> {
                        _loading.value = true
                    }
                    is Result.Error -> {
                        _loading.value = false
                    }
                    is Result.Success -> {
                        _loading.value = false
                        _users.value = tipsResponse.data
                    }
                }
            }
        }
    }
}