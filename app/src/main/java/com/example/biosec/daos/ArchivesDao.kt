package com.example.biosec.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.biosec.entities.Archives

@Dao
interface ArchivesDao {

    @Insert
    fun insert(archive: Archives)

    @Update
    fun update(archive: Archives)

    @Delete
    fun delete(archive: Archives)

    @Query("SELECT * FROM archives ORDER BY website")
    fun getAllArchives(): LiveData<List<Archives>>
}
















