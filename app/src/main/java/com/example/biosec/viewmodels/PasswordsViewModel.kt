package com.example.biosec.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.biosec.entities.Passwords
import com.example.biosec.repositories.PasswordsRepo

class PasswordsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PasswordsRepo(application)
    private val allPasswords = repository.getAllPasswords()

    fun insertPass(pass: Passwords) {
        repository.insertPassword(pass)
    }

    fun updatePass(pass: Passwords) {
        repository.updatePassword(pass)
    }

    fun deletePass(pass: Passwords) {
        repository.deletePassword(pass)
    }

    fun getAllPasswords(): LiveData<List<Passwords>> {
        return allPasswords
    }

}














