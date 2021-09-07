package com.example.passwordmanager

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


val DATABASENAME = "Password Manager"
val TABLENAME = "Passwords"
val COL_NAME = "name"
val COL_PASSWORD = "password"
val COL_WEBLINK = "weblink"
val COL_ID = "id"

class PasswordDataManager(private val context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,
    1) {

    var passwordList = ArrayList<PasswordItem>()

    fun savePassword(pw: PasswordItem) {

        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, pw.name)
        contentValues.put(COL_PASSWORD, pw.passwords.joinToString(","))
        contentValues.put(COL_WEBLINK, pw.weblink)
        val result = database.insert(TABLENAME, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun updatePasswordValue(pw: PasswordItem) {

        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, pw.name)
        contentValues.put(COL_PASSWORD, pw.passwords.joinToString(","))
        contentValues.put(COL_WEBLINK, pw.weblink)
        val rows = database.update(TABLENAME, contentValues, "name = ?", arrayOf<String>(pw.name))

        if (rows == 0) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }


    fun readPasswords(): ArrayList<PasswordItem> {

        val list: ArrayList<PasswordItem> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var user = PasswordItem()
                user.name = result.getString(result.getColumnIndex(COL_NAME))
                val passwords = ArrayList<String>()
                passwords.add(result.getString(result.getColumnIndex(COL_PASSWORD)).toString())
                user.passwords = passwords
                user.weblink = result.getString(result.getColumnIndex(COL_WEBLINK))
                list.add(user)
            }
            while (result.moveToNext())
        }
        return list

//        return passwordList

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLENAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_NAME + " VARCHAR(256)," + COL_WEBLINK + " VARCHAR(256),"+ COL_PASSWORD + " VARCHAR(256))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}