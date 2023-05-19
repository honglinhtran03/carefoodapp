package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class UserAdapter(private val users: List<User>, private val context: Context) : RecyclerView.Adapter<UserAdapter.UserItemViewHolder>() {
    override fun getItemCount(): Int {
        return users.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)

        return UserItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        val u = users[position]
        Picasso.with(context)
            .load(u.avatar_url)
            .into(holder.ivAvatar)
        holder.tvLoginName.text = u.login
        holder.tvId.text = u.id.toString()
    }

    class UserItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvLoginName: TextView = itemView.findViewById(R.id.tv_login_name)
        var tvId: TextView = itemView.findViewById(R.id.tv_id)
        var ivAvatar: ImageView = itemView.findViewById(R.id.iv_avatar)
    }
}
