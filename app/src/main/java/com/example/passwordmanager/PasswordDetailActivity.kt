package com.example.passwordmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_password_details.*

class PasswordDetailActivity: AppCompatActivity()  {

    lateinit var passwordItem: PasswordItem
//    private val listDataManager: ListDataManager = ListDataManager(this)
    var currentIndex: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_details)

        // calling this activity's function to
        // use ActionBar utility methods
        val actionBar = supportActionBar

        // providing title for the ActionBar
        actionBar!!.title = "Password Manager"

        // providing subtitle for the ActionBar
        actionBar.subtitle = "Password Details"

        // adding icon in the ActionBar
//        actionBar.setIcon(R.drawable.app_logo)

        // methods to display the icon in the ActionBar
        actionBar.setDisplayUseLogoEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        passwordItem = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)!!

//        passwordItemList = listDataManager.readLists()
//        passwordTitle = findViewById(R.id.passwordTitle)
//        currentIndex = 0
//        currentIndex = intent.getIntExtra(MainActivity.INTENT_LIST_KEY, -1)
        passwordTitle.text = passwordItem.name
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // method to inflate the options menu when
    // the user opens the menu for the first time
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // methods to control the operations that will
    // happen when user clicks on the action buttons
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show()
            R.id.refresh -> Toast.makeText(this, "Refresh Clicked", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }


}