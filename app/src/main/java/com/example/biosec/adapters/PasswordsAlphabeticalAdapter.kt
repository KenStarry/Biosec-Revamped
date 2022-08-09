package com.example.biosec.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.biosec.R
import com.example.biosec.entities.Passwords

class PasswordsAlphabeticalAdapter : ListAdapter<Passwords, PasswordsAlphabeticalAdapter.PassViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassViewHolder {

        return PassViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.password_alphabetical_item,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: PassViewHolder, position: Int) {

    }

    class PassViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}