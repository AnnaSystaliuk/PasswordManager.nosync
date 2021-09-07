package com.example.passwordmanager

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_password_details.*


class PasswordDetailActivity: AppCompatActivity()  {

    lateinit var passwordItem: PasswordItem

//    private val listDataManager: ListDataManager = ListDataManager(this)
    var currentIndex: Int = -1
    private lateinit var passwordValueEditText : TextInputEditText
    private lateinit var webLinkEditText: TextInputEditText
    private lateinit var saveButton: Button
    private lateinit var openLinkButton: ImageButton
    private val listDataManager: PasswordDataManager = PasswordDataManager(this)


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

        passwordValueEditText = findViewById(R.id.passwordValueEditText)
        openLinkButton = findViewById(R.id.openLinkButton)
        webLinkEditText = findViewById(R.id.webLinkEditText)
        saveButton = findViewById(R.id.saveButton) as Button

        passwordItem = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)!!
        passwordValueEditText.hint = passwordItem.passwords.joinToString(",")
        webLinkEditText.hint = passwordItem.weblink

        passwordTitle.text = passwordItem.name

        saveButton.setOnClickListener {
            var newPwords = ArrayList<String>()
            if (passwordValueEditText.text.toString() != "") {
                newPwords.add(passwordValueEditText.text.toString())
            } else {
                newPwords = passwordItem.passwords
            }
            var newWebLink = ""
            if (webLinkEditText.text.toString() != "") {
                newWebLink = webLinkEditText.text.toString()
                passwordItem.weblink = webLinkEditText.text.toString()
            } else {
                newWebLink = passwordItem.weblink
            }

            val updatedPwItem = PasswordItem(name = passwordItem.name,passwords = newPwords, weblink = newWebLink)
            listDataManager.updatePasswordValue(updatedPwItem)
            listDataManager.readPasswords()
        }

        openLinkButton.setOnClickListener {
            // url form example: https://www.android.com
            var url = passwordItem.weblink
            val webIntent: Intent = Uri.parse(url).let { webpage ->
                Intent(Intent.ACTION_VIEW, webpage)
            }
            try {
                it.context.startActivity(webIntent)
            } catch (e: ActivityNotFoundException) {
                // Define what your app should do if no activity can handle the intent.
                Toast.makeText(this, "Page not found!", Toast.LENGTH_SHORT).show()
            }
        }

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