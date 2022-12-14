package com.example.biosec.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.biosec.daos.PasswordsDao
import com.example.biosec.database.PasswordsDatabase
import com.example.biosec.entities.Passwords
import com.example.biosec.utils.subscribeOnBackground

class PasswordsRepo(application: Application) {

    private var dao: PasswordsDao
    private var allPasswords: LiveData<List<Passwords>>
    private val db = PasswordsDatabase.getInstance(application)

    init {
        dao = db.passwordsDao()
        allPasswords = dao.getAllPasswords()
    }

    fun insertPassword(pass: Passwords) {
        subscribeOnBackground {
            dao.insertPassword(pass)
        }
    }

    fun updatePassword(pass: Passwords) {
        subscribeOnBackground {
            dao.updatePassword(pass)
        }
    }

    fun deletePassword(pass: Passwords) {
        subscribeOnBackground {
            dao.deletePassword(pass)
        }
    }

    fun getAllPasswords(): LiveData<List<Passwords>> {
        return allPasswords
    }

    fun getAlphabeticalPasswords(search: String): LiveData<List<Passwords>> {
        return dao.getAlphabeticalPass(search)
    }

    fun getPasswordAt(pos: Int): LiveData<Passwords> {
        return dao.getPasswordAt(pos)
    }

}










