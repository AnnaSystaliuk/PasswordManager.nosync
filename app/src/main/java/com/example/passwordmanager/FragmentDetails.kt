package com.example.passwordmanager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.fragment_details.*
import android.content.SharedPreferences
import android.widget.Button


class FragmentDetails : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_details,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val preferences = this.activity
            ?.getSharedPreferences("shared", Context.MODE_PRIVATE)
        val currentPassword = preferences?.getString("secretPassword", "")
        val currPwSettings = preferences?.getBoolean("passwordSettings", false)
        editTextPassword.hint = currentPassword
        enablePasswordProtectionSwitch.isChecked = currPwSettings!!

        saveChangesButton.setOnClickListener {
            if (editTextPassword.text.isNotBlank()){
                savePassword(editTextPassword.text.toString())
            }
            val preferences2 = this.activity
                ?.getSharedPreferences("shared", Context.MODE_PRIVATE)?.edit()
            if (enablePasswordProtectionSwitch.isChecked) {
                preferences2?.putBoolean("passwordSettings", true)
            } else {
                preferences2?.putBoolean("passwordSettings", false)
            }
            preferences2?.apply()
            Toast.makeText(this.activity, "Changes applied!", Toast.LENGTH_SHORT).show()
        }

//        enablePasswordProtectionSwitch.liste
    }
    fun savePassword(newPassword: String){
        val preferences = this.activity
            ?.getSharedPreferences("shared", Context.MODE_PRIVATE)?.edit()
        preferences?.putString("secretPassword", newPassword)
        preferences?.apply()
    }

    fun switchPasswordChange(){
        val preferences = this.activity
            ?.getSharedPreferences("shared", Context.MODE_PRIVATE)
        val currPwSettings = preferences?.getBoolean("passwordSettings", false)
        val preferences2 = this.activity
            ?.getSharedPreferences("shared", Context.MODE_PRIVATE)?.edit()
        preferences2?.putBoolean("passwordSettings", !currPwSettings!!)
        preferences2?.apply()
    }




}