package com.example.passwordmanager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PasswordItemAdapter(private val passwords: ArrayList<PasswordItem>, val clickListener: PasswordItemClickListener) : RecyclerView.Adapter<PasswordViewHolder>() {

    interface PasswordItemClickListener {
        fun listItemClicked(list: PasswordItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.items_list_view_holder, parent, false)
        return PasswordViewHolder(view)
    }

    override fun getItemCount(): Int {
        return passwords.size
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {

        holder.listPositionTextView.text = (position + 1).toString()
        holder.listTitleTextView.text = passwords[position].name
        holder.itemView.setOnClickListener {
            clickListener.listItemClicked(passwords[position])
        }
    }

    fun addList(list: PasswordItem) {
        passwords.add(list)
        notifyItemInserted(passwords.size-1)
    }

}