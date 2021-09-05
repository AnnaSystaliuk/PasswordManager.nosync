package com.example.passwordmanager

import android.os.Parcel
import android.os.Parcelable

class PasswordItem(var name: String = "", var passwords: ArrayList<String> = ArrayList()) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.createStringArrayList()!!
    )

    companion object CREATOR: Parcelable.Creator<PasswordItem> {
        override fun createFromParcel(source: Parcel): PasswordItem = PasswordItem(source)

        override fun newArray(size: Int): Array<PasswordItem?> = arrayOfNulls(size)

    }
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeStringList(passwords)
    }

    override fun describeContents() = 0

}