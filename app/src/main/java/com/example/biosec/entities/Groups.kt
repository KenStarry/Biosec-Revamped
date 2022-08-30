package com.example.biosec.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
class Groups(

    @ColumnInfo(name = "group_title") val title: String,
    @ColumnInfo(name = "group_icon") val icon: Int,
    @ColumnInfo(name = "group_color") val color: Int
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "group_id") var id: Int = 0
    set(value) {
        field = value
    }
}