package com.example.biosec.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passwords")
class Passwords(

    @ColumnInfo(name = "website") val website: String?,
    @ColumnInfo(name = "user_name") val userName: String?,
    @ColumnInfo(name = "email_address") val emailAddress: String?,
    @ColumnInfo(name = "password") val password: String?,
    @ColumnInfo(name = "certified") val isCertified: Boolean?,
    @ColumnInfo(name = "locked") val isLocked: Boolean?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
        set(value) {
            field = value
        }
}