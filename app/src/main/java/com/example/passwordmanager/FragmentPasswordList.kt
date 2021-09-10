package com.example.passwordmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_details.*

class FragmentPasswordList : Fragment() {
    private lateinit var todoListRecyclerView: RecyclerView
    private val listDataManager: PasswordDataManager = PasswordDataManager(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.items_list_view_holder,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val lists = listDataManager.readPasswords()
        todoListRecyclerView = findViewById(R.id.lists_recyclerview)
        todoListRecyclerView.layoutManager = LinearLayoutManager(this)
        todoListRecyclerView.adapter = PasswordItemAdapter(lists, this)

}
