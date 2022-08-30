package com.example.biosec.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.biosec.entities.Groups

@Dao
interface GroupsDao {

    @Insert
    fun insertGroup(group: Groups)

    @Update
    fun updateGroup(group: Groups)

    @Delete
    fun deleteGroup(group: Groups)

    @Query("SELECT * FROM groups")
    fun getAllGroups(): LiveData<List<Groups>>

}