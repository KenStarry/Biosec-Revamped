package com.example.biosec.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.biosec.daos.GroupsDao
import com.example.biosec.database.PasswordsDatabase
import com.example.biosec.entities.Groups
import com.example.biosec.utils.subscribeOnBackground
import java.security.acl.Group

class GroupsRepo(application: Application) {

    private var dao: GroupsDao
    private var allGroups: LiveData<List<Groups>>
    private val db = PasswordsDatabase.getInstance(application)

    init {
        dao = db.groupsDao()
        allGroups = dao.getAllGroups()
    }

    fun insertGroup(group: Groups) {
        subscribeOnBackground {
            dao.insertGroup(group)
        }
    }

    fun updateGroup(group: Groups) {
        subscribeOnBackground {
            dao.updateGroup(group)
        }
    }

    fun deleteGroup(group: Groups) {
        subscribeOnBackground {
            dao.deleteGroup(group)
        }
    }

    fun getAllGroups(): LiveData<List<Groups>> {
        return allGroups
    }
}








