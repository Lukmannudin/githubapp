package com.example.searchusergithubrepository

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.searchusergithubrepository.data.User
import com.facebook.drawee.view.SimpleDraweeView

class CardAdapter(
    var users: MutableList<User> = mutableListOf()
) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: SimpleDraweeView = itemView.findViewById(R.id.thumbnail_user)
        val username: TextView = itemView.findViewById(R.id.github_user_name)

        fun bindItem(user: User) {
            thumbnail.setImageURI(user.avatarUrl)
            username.text = user.login
        }
    }


}
