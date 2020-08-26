package com.example.searchusergithubrepository.main

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.searchusergithubrepository.CardAdapter
import com.example.searchusergithubrepository.R
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
        setContentView()
        adapter = CardAdapter()

        rv_users.layoutManager = LinearLayoutManager(this)
        rv_users.adapter = adapter
        rv_users.addItemDecoration(RecyclerViewItemDecoration(this,18f))
    }

    override fun onStart() {
        super.onStart()
        viewModel.users.observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter.users = it.toMutableList()
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun setContentView() {
        setSearchView()

    }

    private fun setSearchView(){
        search.setOnEditorActionListener { view, i, keyEvent  ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                viewModel.search(view.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }
}