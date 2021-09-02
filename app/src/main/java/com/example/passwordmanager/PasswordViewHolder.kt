package com.example.passwordmanager

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PasswordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var listPositionTextView = itemView.findViewById<TextView>(R.id.itemNumber)
    var listTitleTextView = itemView.findViewById<TextView>(R.id.itemString)
}