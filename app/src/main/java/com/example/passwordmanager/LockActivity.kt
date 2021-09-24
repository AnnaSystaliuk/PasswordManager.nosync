package com.example.passwordmanager

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_lock.*

class LockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock)

        Unlock_Button.setOnClickListener {
            if (passcode_field.text.toString() == "1234") {
                finish()
            }else {
                val imm = this@LockActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                Toast.makeText(this@LockActivity, "Invalid Passcode", Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onBackPressed() {
        Toast.makeText(this@LockActivity, "Please Enter the password to unlock app!", Toast.LENGTH_SHORT).show()
    }
}