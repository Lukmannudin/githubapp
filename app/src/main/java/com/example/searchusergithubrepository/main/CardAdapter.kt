package com.example.searchusergithubrepository.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.searchusergithubrepository.R
import com.example.searchusergithubrepository.USERDATA
import com.example.searchusergithubrepository.data.User
import com.example.searchusergithubrepository.main.userdetail.UserDetailActivity
import com.facebook.drawee.view.SimpleDraweeView
import timber.log.Timber

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
        holder.card.setOnClickListener {
            val intent = Intent(it.context, UserDetailActivity::class.java)
            intent.putExtra(USERDATA, users[position])
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val thumbnail: SimpleDraweeView = itemView.findViewById(R.id.thumbnail_user)
        private val username: TextView = itemView.findViewById(R.id.github_user_name)
        val card: CardView = itemView.findViewById(R.id.card)

        fun bindItem(user: User) {
            thumbnail.setImageURI(user.avatarUrl)
            username.text = user.login
        }
    }


}
