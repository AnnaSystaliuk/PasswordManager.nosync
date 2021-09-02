package com.example.passwordmanager

import android.content.Context
import androidx.preference.PreferenceManager

class PasswordDataManager(private val context: Context) {
    fun saveList(list: PasswordItem) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPrefs.putStringSet(list.name, list.passwords.toHashSet())
        sharedPrefs.apply()
    }

    fun readLists(): ArrayList<PasswordItem> {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val contents = sharedPrefs.all
        val taskLists = ArrayList<PasswordItem>()

        for (taskList in contents) {
            val taskItems = ArrayList(taskList.value as HashSet<String>)
            val list = PasswordItem(taskList.key, taskItems)
            taskLists.add(list)
        }

        return taskLists

    }
}