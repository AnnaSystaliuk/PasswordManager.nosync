package com.example.passwordmanager

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), PasswordItemAdapter.PasswordItemClickListener {

    private lateinit var todoListRecyclerView: RecyclerView
    private val listDataManager: PasswordDataManager = PasswordDataManager(this)

    companion object {
        const val INTENT_LIST_KEY = "list"
        const val LIST_DETAIL_REQUEST_CODE = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    private fun showCreateTodoListDialog() {

        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)
        val myDialog = AlertDialog.Builder(this)
        val todoTitleEditText = EditText(this)
        todoTitleEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

        myDialog.setTitle(dialogTitle)
        myDialog.setView(todoTitleEditText)

        myDialog.setPositiveButton(positiveButtonTitle) {
                dialog, _ ->
            val adapter = todoListRecyclerView.adapter as PasswordItemAdapter
            val pwItem = PasswordItem(todoTitleEditText.text.toString())
            listDataManager.savePassword(pwItem)
            adapter.addList(pwItem)
            dialog.dismiss()
            showTaskListItems(pwItem)
        }
        myDialog.create().show()
    }

    private fun showTaskListItems(list: PasswordItem) {
        val taskListItem = Intent(this, PasswordDetailActivity::class.java)
        taskListItem.putExtra(INTENT_LIST_KEY, list)
        startActivity(taskListItem)
    }

    override fun listItemClicked(list: PasswordItem) {
        showTaskListItems(list)
    }

}
