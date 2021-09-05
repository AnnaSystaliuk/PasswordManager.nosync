package com.example.passwordmanager

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.widget.Toast
import androidx.preference.PreferenceManager

//object PasswordReaderContract {
//    // Table contents are grouped together in an anonymous object.
//    object PasswordEntry : BaseColumns {
//        const val TABLE_NAME = "entry"
//        const val COLUMN_NAME_TITLE = "title"
//        const val COLUMN_NAME_SUBTITLE = "subtitle"
//    }
//}

//class MyDBHelper(context:Context) : SQLiteOpenHelper(context,"pppp",null,1) {
//    override fun onCreate(db: SQLiteDatabase?) {
//        db?.execSQL("CREATE TABLE PASSWORDS(PASSWORD_ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PASSWORD TEXT)")
//        db?.execSQL("INSERT INTO PASSWORDS(NAME, PASSWORD) VALUES('FIRST DB ENTRY!!!','myNewPassword')")
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        TODO("Not yet implemented")
//    }
//
//}

val DATABASENAME = "Password Manager"
val TABLENAME = "Passwords"
val COL_NAME = "name"
val COL_PASSWORD = "password"
val COL_ID = "id"

class PasswordDataManager(private val context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,
    1) {
//    var helper = MyDBHelper(context)
//    var db = helper.writableDatabase
    var passwordList = ArrayList<PasswordItem>()

//    init{
//        val taskLists = ArrayList<PasswordItem>()
//        var rs = db.rawQuery("SELECT * FROM PASSWORDS",null)
//
//        //if db is created, fill out taskLists
//        if (rs.moveToNext()){
//            var newItem = PasswordItem(rs.getString(1),arrayListOf(rs.getString(2)))
//            taskLists += newItem
//        }
//        passwordList = taskLists
//    }


    fun savePassword(pw: PasswordItem) {

        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, pw.name)
        contentValues.put(COL_PASSWORD, pw.passwords.joinToString(","))
        val result = database.insert(TABLENAME, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }

//        passwordList.add(pw)

//        var cv = ContentValues()
//        cv.put("NAME",pw.name )
//        cv.put("PASSWORD",pw.passwords.joinToString(",") )
//        db.insert("PASSWORDS", null, cv)

//        val builder = StringBuilder()
//        builder.append("INSERT INTO PASSWORDS(NAME, PASSWORD) VALUES('")
//            .append(pw.name)
//            .append("','")
//            .append(pw.passwords.joinToString(","))
//            .append("')")
//        val query = builder.toString()
//        db.execSQL(query)


//        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context).edit()
//        sharedPrefs.putStringSet(pw.name, pw.passwords.toHashSet())
//        sharedPrefs.apply()
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
                list.add(user)
            }
            while (result.moveToNext())
        }
        return list

//        return passwordList

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLENAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_NAME + " VARCHAR(256)," + COL_PASSWORD + " VARCHAR(256))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}