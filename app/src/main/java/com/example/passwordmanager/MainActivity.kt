package com.example.passwordmanager

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import android.R.drawable
import android.content.Context

class MainActivity : AppCompatActivity(), PasswordItemAdapter.PasswordItemClickListener {

    private lateinit var todoListRecyclerView: RecyclerView
    private val listDataManager: PasswordDataManager = PasswordDataManager(this)
    var atHomePage : Boolean = true
    var passwordCheckPassed: Boolean = false

    companion object {
        const val INTENT_LIST_KEY = "list"
        const val LIST_DETAIL_REQUEST_CODE = 123
    }

    override fun onStart() {
        super.onStart()
        updateLists()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        atHomePage = true
        passwordCheckPassed=false
        val actionBar = supportActionBar
        // methods to display the icon in the ActionBar
//        actionBar!!.setDisplayUseLogoEnabled(true)
//        actionBar.setDisplayShowHomeEnabled(true)
//        actionBar.setDisplayHomeAsUpEnabled(true)


//        var helper = MyDBHelper(applicationContext)
//        var db = helper.readableDatabase


//        var rs2 = db.rawQuery("INSERT INTO PASSWORDS(NAME, PASSWORD) VALUES('SMOOTHIES FOR LYFE','hehehe')",null)
//        var rs = db.rawQuery("SELECT * FROM PASSWORDS",null)
        //if db is created
//        if (rs.moveToNext())
//            Toast.makeText(applicationContext, rs.getString(3),Toast.LENGTH_LONG).show()

        val lists = listDataManager.readPasswords()
        todoListRecyclerView = findViewById(R.id.lists_recyclerview)
        todoListRecyclerView.layoutManager = LinearLayoutManager(this)
        todoListRecyclerView.adapter = PasswordItemAdapter(lists, this)


        fab.setOnClickListener { _ ->
            showCreateTodoListDialog()
        }

        val preferences = this.getSharedPreferences("shared", Context.MODE_PRIVATE)
        val currPwSettings = preferences?.getBoolean("passwordSettings", true)
        if (currPwSettings == true && passwordCheckPassed == false){
            val fram = supportFragmentManager.beginTransaction()
            fram.replace(
                com.example.passwordmanager.R.id.fragment_details,
                com.example.passwordmanager.PasswordProtectionFragment()
            )
            fram.commit()
        }

    }

    fun switchPage() {
        if (atHomePage == true) {
            val fram = supportFragmentManager.beginTransaction()
            fram.replace(
                com.example.passwordmanager.R.id.fragment_details,
                com.example.passwordmanager.FragmentDetails()
            )
            fram.commit()

        } else {
            val fram = supportFragmentManager.beginTransaction()
            fram.replace(
                com.example.passwordmanager.R.id.fragment_details,
                com.example.passwordmanager.FragmentPasswordListDetails()
            )
            fram.commit()
        }

        atHomePage = !atHomePage

    }
    

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LIST_DETAIL_REQUEST_CODE) {
            data?.let {
                val list = data.getParcelableExtra<PasswordItem>(INTENT_LIST_KEY)!!
                listDataManager.savePassword(list)
                updateLists()
            }
        }
    }

    private fun updateLists() {
        val lists = listDataManager.readPasswords()
        todoListRecyclerView.adapter = PasswordItemAdapter(lists, this)
    }


    private fun showCreateTodoListDialog() {

        val dialogTitle = "New password information"
        val positiveButtonTitle = getString(R.string.create_list)
        val myDialog = AlertDialog.Builder(this)
        val passwordNameEditText = EditText(this)

        passwordNameEditText.hint = "Password name:"
        passwordNameEditText.setPadding(50)

        passwordNameEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

        myDialog.setTitle(dialogTitle)
        myDialog.setView(passwordNameEditText)

        myDialog.setPositiveButton(positiveButtonTitle) {
                dialog, _ ->
            val adapter = todoListRecyclerView.adapter as PasswordItemAdapter
            val pwItem = PasswordItem(passwordNameEditText.text.toString())
            listDataManager.savePassword(pwItem)
            adapter.addList(pwItem)
            dialog.dismiss()
            showTaskListItems(pwItem)
        }
        myDialog.create().show()
    }

    private fun showTaskListItems(pw: PasswordItem) {
        val taskListItem = Intent(this, PasswordDetailActivity::class.java)
        taskListItem.putExtra(INTENT_LIST_KEY, pw)
        startActivity(taskListItem)
    }

    override fun listItemClicked(list: PasswordItem) {
        showTaskListItems(list)
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
//            R.id.passwordSettings -> Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show()
            R.id.passwordSettings -> {
                switchPage()
                if (atHomePage == true){
                    item.setIcon(android.R.drawable.ic_secure)
                } else {
                    item.setIcon(android.R.drawable.ic_menu_revert)
                }
            }
            R.id.refresh -> updateLists()
        }
        return super.onOptionsItemSelected(item)
    }

}
