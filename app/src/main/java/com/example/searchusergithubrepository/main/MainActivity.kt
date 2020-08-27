package com.example.searchusergithubrepository.main

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.searchusergithubrepository.CardAdapter
import com.example.searchusergithubrepository.R
import com.example.searchusergithubrepository.data.User
import com.example.searchusergithubrepository.viewmodelfactory.UserViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CardAdapter

    private val viewModel: MainViewModel by viewModels {
        UserViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = CardAdapter()

        setContentView()
    }

    override fun onStart() {
        super.onStart()
        setUpObservation()
    }

    private fun setUpObservation() {
        showLoading(false)
        viewModel.viewState.observe(this, { state ->
            when (state) {
                is MainViewModel.MainViewState.Loading -> {
                    showLoading(true)
                }

                is MainViewModel.MainViewState.UsersLoaded -> {
                    showLoading(false)
                    showListUsers(state.users)
                    setViewDescription(false)

                    if (state.users.isEmpty()) {
                        setViewDescription(true)
                        showNotFound()
                    }
                }

                is MainViewModel.MainViewState.UserLoadFailure -> {
                    showLoading(false)
                    showLimitedAccess()
                    setViewDescription(true)
                }
            }
        })
    }

    private fun setViewDescription(status: Boolean) {
        if (status) {
            home_jumbotron.visibility = View.VISIBLE
            home_description.visibility = View.VISIBLE
        } else {
            home_jumbotron.visibility = View.GONE
            home_description.visibility = View.GONE
        }
    }

    private fun showLoading(status: Boolean) {
        if (status) {
            main_progressbar.visibility = View.VISIBLE
        } else {
            main_progressbar.visibility = View.GONE
        }
    }

    private fun showNotFound() {
        home_jumbotron.setImageResource(R.drawable.icon_notfound)
        home_description.text = getString(R.string.sorry_user_not_found)
    }

    private fun showLimitedAccess() {
        showListUsers(mutableListOf())
        home_jumbotron.setImageResource(R.drawable.icon_sad)
        home_description.text = getString(R.string.limit_access_reached)
    }

    private fun showGreetingView() {
        home_jumbotron.setImageResource(R.drawable.icon_letsearch)
        home_description.text = getString(R.string.letsearch)
    }

    private fun showListUsers(users: List<User>) {
        adapter.users.clear()
        adapter.users.addAll(users)
        adapter.notifyDataSetChanged()
    }

    private fun setContentView() {
        showGreetingView()
        setSearchView()
        setUserRecyclerView()
    }

    private fun setUserRecyclerView() {
        rv_users.layoutManager = LinearLayoutManager(this)
        rv_users.adapter = adapter
        rv_users.addItemDecoration(RecyclerViewItemDecoration(this, 28f))
    }

    private fun setSearchView() {
        search.setOnEditorActionListener { view, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                if (!view.text.isBlank()) {
                    viewModel.search(view.text.toString())
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }
}