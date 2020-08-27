package com.example.searchusergithubrepository.main

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchusergithubrepository.CardAdapter
import com.example.searchusergithubrepository.R
import com.example.searchusergithubrepository.data.User
import com.example.searchusergithubrepository.viewmodelfactory.UserViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CardAdapter
    var currentPage = 0

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
        adapter.users.clear()
        showListUsers(mutableListOf())
        home_jumbotron.setImageResource(R.drawable.icon_notfound)
        home_description.text = getString(R.string.sorry_user_not_found)
    }

    private fun showLimitedAccess() {
        adapter.users.clear()
        home_jumbotron.setImageResource(R.drawable.icon_sad)
        home_description.text = getString(R.string.limit_access_reached)
    }

    private fun showGreetingView() {
        home_jumbotron.setImageResource(R.drawable.icon_letsearch)
        home_description.text = getString(R.string.letsearch)
    }

    private fun showListUsers(users: List<User>) {
        adapter.users.addAll(users)
        adapter.notifyDataSetChanged()
    }

    private fun setContentView() {
        showGreetingView()
        setSearchView()
        setUserRecyclerView()
    }

    private fun setUserRecyclerView() {
        val layoutManager = LinearLayoutManager(this)

        rv_users.layoutManager = layoutManager
        rv_users.adapter = adapter
        rv_users.addItemDecoration(RecyclerViewItemDecoration(this, 28f))

        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()

                if (lastVisibleItemPosition == adapter.users.size - 1) {
                    viewModel.search(search.text.toString(), currentPage++)
                }
            }
        }

        rv_users.addOnScrollListener(scrollListener)

    }

    private fun setSearchView() {
        search.setOnEditorActionListener { view, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                if (!view.text.isBlank()) {
                    adapter.users.clear()
                    viewModel.search(view.text.toString(), currentPage)
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }
}