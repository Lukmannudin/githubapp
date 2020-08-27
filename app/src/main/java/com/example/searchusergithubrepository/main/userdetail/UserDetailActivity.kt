package com.example.searchusergithubrepository.main.userdetail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.searchusergithubrepository.R
import com.example.searchusergithubrepository.USERDATA
import com.example.searchusergithubrepository.data.Repo
import com.example.searchusergithubrepository.data.User
import com.example.searchusergithubrepository.main.RecyclerViewItemDecoration
import com.example.searchusergithubrepository.viewmodelfactory.UserViewModelFactory
import kotlinx.android.synthetic.main.user_detail.*

class UserDetailActivity : AppCompatActivity() {


    private lateinit var user: User
    private lateinit var adapter: RepoCardAdapter

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

        adapter = RepoCardAdapter()
        rv_repo.layoutManager = LinearLayoutManager(this)
        rv_repo.adapter = adapter
        rv_repo.addItemDecoration(RecyclerViewItemDecoration(this,12f))

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
                    showUserStateLoading(true)
                }
                is UserDetailViewModel.UserDetailState.UserLoaded -> {
                    showUserStateLoading(false)
                    refreshUserView(state.user)
                }
                is UserDetailViewModel.UserDetailState.UserLoadFailure -> {
                    showUserStateLoading(false)
                    Toast.makeText(this, "Cannot Connect to Server", Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.repoViewState.observe(this, { state ->
            when (state) {
                is UserDetailViewModel.RepoDetailState.loading -> {
                    showRepoStateLoading(true)
                }
                is UserDetailViewModel.RepoDetailState.RepoLoaded -> {
                    showRepoStateLoading(false)
                    if (state.repos.isNotEmpty()) {
                        refreshRepoView(state.repos)
                    }
                }
                is UserDetailViewModel.RepoDetailState.RepoLoadFailure -> {
                    showRepoStateLoading(false)
                    Toast.makeText(this, "Cannot Connect to Server", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun showUserStateLoading(status: Boolean) {
        userDetail_swipeRefresh.isRefreshing = status
    }

    private fun showRepoStateLoading(status: Boolean) {
        if (status){
            repo_proggressbar.visibility = View.VISIBLE
        } else {
            repo_proggressbar.visibility = View.GONE

        }
    }

    private fun refreshUserView(user: User) {
        userDetail_thumbnail.setImageURI(user.avatarUrl)
        userDetail_following.text = user.following.toString()
        userDetail_followers.text = user.followers.toString()
    }

    private fun refreshRepoView(repos: List<Repo>) {
        adapter.repos.clear()
        adapter.repos.addAll(repos)
        adapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}