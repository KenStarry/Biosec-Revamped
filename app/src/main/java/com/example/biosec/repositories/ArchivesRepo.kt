package com.example.biosec.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.biosec.daos.ArchivesDao
import com.example.biosec.database.PasswordsDatabase
import com.example.biosec.entities.Archives
import com.example.biosec.utils.subscribeOnBackground

class ArchivesRepo(application: Application) {

    private var dao: ArchivesDao
    private var allArchives: LiveData<List<Archives>>
    private val db = PasswordsDatabase.getInstance(application)

    init {
        dao = db.archivesDao()
        allArchives = dao.getAllArchives()
    }

    fun insertArchive(archive: Archives) {
        subscribeOnBackground {
            dao.insert(archive)
        }
    }

    fun updateArchive(archive: Archives) {
        subscribeOnBackground {
            dao.update(archive)
        }
    }

    fun deleteArchive(archive: Archives) {
        subscribeOnBackground {
            dao.delete(archive)
        }
    }

    fun getAllArchives(): LiveData<List<Archives>> {
        return allArchives
    }
}













