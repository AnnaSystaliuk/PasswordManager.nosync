package com.example.passwordmanager

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.password_list_item.*
import android.widget.LinearLayout
import android.R.layout
import androidx.fragment.app.Fragment
import java.util.*
import android.R.id.*
import android.content.Intent
import android.view.MenuItem
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var passwords: ArrayList<String> get() {
        return arrayListOf(*listDataManager.readPasswords().map { item -> item.weblink  }.toTypedArray())
    } set(value) {}

    lateinit var arrAdapt: ArrayAdapter<String>
    private val listDataManager: PasswordDataManager = PasswordDataManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arrAdapt = ArrayAdapter<String>(this, R.layout.password_list_item, passwords)
        password_list.adapter = arrAdapt

        password_list.setOnItemClickListener { adapterView, view, position, id ->
            var mainFragment = PasswordDetails.createPasswordDetail(listDataManager.readPasswords()[position])
            supportFragmentManager.beginTransaction()
                .add(R.id.frameLayout, mainFragment, "details")
                .addToBackStack("details").commit()

            supportFragmentManager.addOnBackStackChangedListener {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    add_new_password_button.hide()
                }else {
                    add_new_password_button.show()
                }
            }
        }

        add_new_password_button.setOnClickListener {
            viewInputDialog()
        }

        val intent = Intent(this, LockActivity::class.java)
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        password_list.alpha = 0.0F
    }

    override fun onResume() {
        super.onResume()
        password_list.alpha = 1.0F
    }

    fun viewInputDialog() {
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Add a new password")

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val input = EditText(this)
        input.setHint("Website URL")
        input.inputType = InputType.TYPE_TEXT_VARIATION_URI
        layout.addView(input)

        val username = EditText(this)
        username.setHint("Email")
        username.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        layout.addView(username)


        val passwordField = EditText(this)
        passwordField.setHint("Password")
        passwordField.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        layout.addView(passwordField)

        builder.setView(layout)

        builder.setPositiveButton("Add", DialogInterface.OnClickListener { dialog, which ->
            // Here you get get input text from the Edittext
            val websiteValue = input.text.toString()
            val usernameValue = username.text.toString()
            val passwordValue = passwordField.text.toString()
            val pwItem = PasswordItem(usernameValue, arrayListOf(passwordValue), websiteValue)
            listDataManager.savePassword(pwItem)
            arrAdapt.clear()
            arrAdapt.addAll(passwords)
            arrAdapt.notifyDataSetChanged()
        })

        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return false
    }
}