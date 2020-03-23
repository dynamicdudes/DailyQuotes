package com.dynamicdudes.kotlinretrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_row.view.*

class UsersAdapter(private val users: List<User>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_row,parent,false)
        return ViewHolder(view)

    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user  = users[position]
        holder.firstName.text = "\"${user.text}\""
        holder.lastName.text = "- ${user.author}"
    }


    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){

        val firstName = itemView.first_name
        val lastName = itemView.last_name

    }

}
