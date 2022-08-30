package com.example.biosec.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.biosec.entities.Groups
import com.example.biosec.repositories.GroupsRepo

class GroupsViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = GroupsRepo(application)
    private val allGroups = repo.getAllGroups()

    fun insertGroup(group: Groups) {
        repo.insertGroup(group)
    }

    fun updateGroup(group: Groups) {
        repo.updateGroup(group)
    }

    fun deleteGroup(group: Groups) {
        repo.deleteGroup(group)
    }

    fun getAllGroups(): LiveData<List<Groups>> {
        return allGroups
    }
}