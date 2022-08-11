package com.example.biosec.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.biosec.entities.Archives
import com.example.biosec.repositories.ArchivesRepo

class ArchivesViewModel(application: Application) : AndroidViewModel(application) {

    private var archivesRepo = ArchivesRepo(application)
    private var allArchives = archivesRepo.getAllArchives()

    fun insertArchive(archive: Archives) {
        archivesRepo.insertArchive(archive)
    }

    fun updateArchive(archive: Archives) {
        archivesRepo.updateArchive(archive)
    }

    fun deleteArchive(archive: Archives) {
        archivesRepo.deleteArchive(archive)
    }

    fun getAllArchives(): LiveData<List<Archives>> {
        return allArchives
    }
}