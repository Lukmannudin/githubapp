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
) : ViewModel() {


    private val _viewState = MutableLiveData<MainViewState>()
    val viewState: LiveData<MainViewState> = _viewState

    fun search(searchWord: String) {
        viewModelScope.launch {
            val users = userRepository.search(searchWord)
            users.collect { tipsResponse ->
                when (tipsResponse) {
                    is Result.Loading -> {
                        _viewState.value = MainViewState.Loading
                    }
                    is Result.Error -> {
                        _viewState.value = MainViewState.UserLoadFailure
                    }
                    is Result.Success -> {
                        _viewState.value = MainViewState.UsersLoaded(tipsResponse.data)
                    }
                }
            }
        }
    }

    sealed class MainViewState {
        object Loading : MainViewState()

        data class UsersLoaded(val users: List<User>) : MainViewState()

        object UserLoadFailure : MainViewState()
    }
}