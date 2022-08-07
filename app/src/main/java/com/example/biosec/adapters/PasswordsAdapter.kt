package com.example.biosec.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.biosec.R
import com.example.biosec.entities.Passwords

class PasswordsAdapter : ListAdapter<Passwords, PasswordsAdapter.PasswordHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordHolder {

        return PasswordHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.password_item,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: PasswordHolder, position: Int) {
        with(getItem(position)) {

            holder.passTitle.text = website
            holder.passDesc.text = emailAddress

            if (isCertified == true)
                holder.passVerifiedIc.visibility = View.VISIBLE
            else
                holder.passVerifiedIc.visibility = View.GONE

            if (isLocked == true)
                holder.passSecurityIc.visibility = View.VISIBLE
            else
                holder.passSecurityIc.visibility = View.GONE
        }
    }

    fun getPasswordAt(position: Int) = getItem(position)

    class PasswordHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val passTitle: TextView = itemView.findViewById(R.id.passTitle)
        val passDesc: TextView = itemView.findViewById(R.id.passDescription)
        val passVerifiedIc: ImageView = itemView.findViewById(R.id.passVerifiedIc)
        val passSecurityIc: ImageView = itemView.findViewById(R.id.passSecurityIc)
    }
}

private val diffCallback = object : DiffUtil.ItemCallback<Passwords>() {
    override fun areItemsTheSame(oldItem: Passwords, newItem: Passwords): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Passwords, newItem: Passwords): Boolean {
        return oldItem.id == newItem.id &&
                oldItem.website == newItem.website &&
                oldItem.userName == newItem.userName &&
                oldItem.password == newItem.password &&
                oldItem.emailAddress == newItem.emailAddress &&
                oldItem.isLocked == newItem.isLocked &&
                oldItem.isCertified == newItem.isCertified
    }
}