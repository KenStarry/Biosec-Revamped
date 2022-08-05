package com.example.biosec.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.biosec.entities.Passwords

@Dao
interface PasswordsDao {

    @Insert
    fun insertPassword(pass: Passwords)

    @Update
    fun updatePassword(pass: Passwords)

    @Delete
    fun deletePassword(pass: Passwords)

    @Query("SELECT * FROM passwords")
    fun getAllPasswords(): LiveData<List<Passwords>>

}










