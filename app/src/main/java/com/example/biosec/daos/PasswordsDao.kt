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

    @Query("SELECT * FROM passwords ORDER BY website")
    fun getAllPasswords(): LiveData<List<Passwords>>

    @Query("SELECT * FROM passwords WHERE website LIKE :search ORDER BY website")
    fun getAlphabeticalPass(search: String): LiveData<List<Passwords>>

    @Query("SELECT * FROM passwords WHERE id = :id")
    fun getPasswordAt(id: Int): LiveData<Passwords>
}










