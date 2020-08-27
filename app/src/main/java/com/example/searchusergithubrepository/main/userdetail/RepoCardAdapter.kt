package com.example.searchusergithubrepository.main.userdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.searchusergithubrepository.R
import com.example.searchusergithubrepository.data.Repo

class RepoCardAdapter(
    var repos: MutableList<Repo> = mutableListOf()
) : RecyclerView.Adapter<RepoCardAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(repos[position])
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    class ViewHolder(itemVew: View) : RecyclerView.ViewHolder(itemVew) {
        private val name: TextView = itemVew.findViewById(R.id.repo_name)
        private val starredCount: TextView = itemVew.findViewById(R.id.repo_starredcount)

        fun bindData(repo: Repo) {
            name.text = repo.name
            starredCount.text = repo.stargazersCount.toString()
        }
    }
}