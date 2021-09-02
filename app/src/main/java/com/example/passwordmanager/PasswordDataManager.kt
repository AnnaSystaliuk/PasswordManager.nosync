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

class MyDBHelper(context:Context) : SQLiteOpenHelper(context,"USERDB",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE PASSWORDS(PASSWORD_ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PASSWORD TEXT)")
        db?.execSQL("INSERT INTO PASSWORDS(NAME, PASSWORD) VALUES('ASOS online shop','myNewPassword')")
        db?.execSQL("INSERT INTO PASSWORDS(NAME, PASSWORD) VALUES('APPLE','EamParamPamPam')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}

class PasswordDataManager(private val context: Context) {
    var helper = MyDBHelper(context)
    var db = helper.writableDatabase
    var passwordList = ArrayList<PasswordItem>()

    init{
        val taskLists = ArrayList<PasswordItem>()
        var rs = db.rawQuery("SELECT * FROM PASSWORDS",null)

        //if db is created, fill out taskLists
        if (rs.moveToNext()){
            var newItem = PasswordItem(rs.getString(1),arrayListOf(rs.getString(2)))
            taskLists += newItem
        }
        passwordList = taskLists
    }


    fun savePassword(pw: PasswordItem) {
        passwordList.add(pw)

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

        return passwordList

    }
}