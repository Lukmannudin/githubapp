package com.example.searchusergithubrepository.main.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchusergithubrepository.data.Repo
import com.example.searchusergithubrepository.data.Result
import com.example.searchusergithubrepository.data.User
import com.example.searchusergithubrepository.data.user.UserRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userViewState = MutableLiveData<UserDetailState>()
    val userViewState: LiveData<UserDetailState> = _userViewState

    private val _repoViewState = MutableLiveData<RepoDetailState>()
    val repoViewState: LiveData<RepoDetailState> = _repoViewState

    fun refreshUser(userName: String) {
        viewModelScope.launch {
            val user = userRepository.fetchUser(userName)
            user.collect { userResponse ->
                when (userResponse) {
                    is Result.Loading -> {
                        _userViewState.value = UserDetailState.loading
                    }
                    is Result.Success -> {
                        _userViewState.value = UserDetailState.UserLoaded(userResponse.data)
                    }
                    is Result.Error -> {
                        _userViewState.value = UserDetailState.UserLoadFailure(userResponse.exception)
                    }
                }
            }
        }
    }

    fun refreshRepos(userName: String) {
        viewModelScope.launch {
            val repos = userRepository.fetchRepos(userName)
            repos.collect { reposResponse ->
                when (reposResponse) {
                    is Result.Loading -> {
                        _repoViewState.value = RepoDetailState.loading
                    }
                    is Result.Success -> {
                        _repoViewState.value = RepoDetailState.RepoLoaded(reposResponse.data)
                    }
                    is Result.Error -> {
                        _repoViewState.value = RepoDetailState.RepoLoadFailure(reposResponse.exception)
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

    sealed class RepoDetailState {
        object loading : RepoDetailState()

        data class RepoLoaded(val repos: List<Repo>) : RepoDetailState()

        data class RepoLoadFailure(val exception: Exception) : RepoDetailState()
    }
}