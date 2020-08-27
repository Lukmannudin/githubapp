package com.example.searchusergithubrepository.main.userdetail

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.searchusergithubrepository.R
import com.example.searchusergithubrepository.USERDATA
import com.example.searchusergithubrepository.data.User
import com.example.searchusergithubrepository.viewmodelfactory.UserViewModelFactory
import kotlinx.android.synthetic.main.user_detail.*
import timber.log.Timber

class UserDetailActivity : AppCompatActivity() {


    private lateinit var user: User

    private val viewModel: UserDetailViewModel by viewModels {
        UserViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_detail)
        setSupportActionBar(userDetail_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Back"

        user = intent.getParcelableExtra(USERDATA)!!

        refreshUserView(user)

        userDetail_swipeRefresh.setOnRefreshListener {
            viewModel.refreshUser(user.login)
        }
    }

    override fun onStart() {
        super.onStart()
        setUpObservation()
        viewModel.refreshUser(user.login)
        viewModel.refreshRepos(user.login)
    }

    private fun setUpObservation() {
        viewModel.userViewState.observe(this, { state ->
            when (state) {
                is UserDetailViewModel.UserDetailState.loading -> {
                    showLoading(true)
                }
                is UserDetailViewModel.UserDetailState.UserLoaded -> {
                    showLoading(false)
                    refreshUserView(state.user)
                }
                is UserDetailViewModel.UserDetailState.UserLoadFailure -> {
                    showLoading(false)
                    Toast.makeText(this, "Cannot Connect to Server", Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.repoViewState.observe(this, { state ->
            when (state) {
                is UserDetailViewModel.RepoDetailState.loading -> {
                    showLoading(true)
                }
                is UserDetailViewModel.RepoDetailState.RepoLoaded -> {
                    showLoading(false)
//                    refreshUserView(state.repos)
                    if (state.repos.isNotEmpty()){
                        Timber.d("ada datanya: ${state.repos[0].name}")
                    }
                }
                is UserDetailViewModel.RepoDetailState.RepoLoadFailure -> {
                    showLoading(false)
                    Toast.makeText(this, "Cannot Connect to Server", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun showLoading(status: Boolean) {
        Timber.d("hahaha keload $status")

        userDetail_swipeRefresh.isRefreshing = status
    }

    private fun refreshUserView(user: User) {
        userDetail_thumbnail.setImageURI(user.avatarUrl)
        userDetail_following.text = user.following.toString()
        userDetail_followers.text = user.followers.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}