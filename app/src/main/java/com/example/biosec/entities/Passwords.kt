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
    @ColumnInfo(name = "password_strength_ic") val passStrengthIcon: Int?,
    @ColumnInfo(name = "locked") val isLocked: Boolean?,
    @ColumnInfo(name = "pass_icon") val passIcon: Int?,
    @ColumnInfo(name = "pass_color") val passColor: Int?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
        set(value) {
            field = value
        }
}