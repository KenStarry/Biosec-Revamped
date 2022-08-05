package com.example.biosec.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passwords")
class Passwords(

    @ColumnInfo(name = "user_name") val userName: String?,
    @ColumnInfo(name = "email_address") val emailAddress: String?,
    @ColumnInfo(name = "password") val password: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
        set(value) {
            field = value
        }
}