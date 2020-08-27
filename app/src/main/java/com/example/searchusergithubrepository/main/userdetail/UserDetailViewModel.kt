package com.example.searchusergithubrepository.main.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchusergithubrepository.data.Result
import com.example.searchusergithubrepository.data.User
import com.example.searchusergithubrepository.data.user.UserRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _viewState = MutableLiveData<UserDetailState>()
    val viewState: LiveData<UserDetailState> = _viewState

    fun refreshUser(userName: String) {
        viewModelScope.launch {
            val user = userRepository.fetchUser(userName)
            user.collect { userResponse ->
                when (userResponse) {
                    is Result.Loading -> {
                        _viewState.value = UserDetailState.loading
                    }
                    is Result.Success -> {
                        _viewState.value = UserDetailState.UserLoaded(userResponse.data)
                    }
                    is Result.Error -> {
                        _viewState.value = UserDetailState.UserLoadFailure(userResponse.exception)
                    }
                }
            }
        }
    }

    sealed class UserDetailState {
        object loading : UserDetailState()

        data class UserLoaded(val user: User) : UserDetailState()

        data class UserLoadFailure(val exception: Exception) : UserDetailState()
    }
}