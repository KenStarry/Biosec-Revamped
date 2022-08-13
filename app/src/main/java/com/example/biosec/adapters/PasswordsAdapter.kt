package com.example.biosec.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SectionIndexer
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.biosec.R
import com.example.biosec.entities.Passwords
import com.example.biosec.interfaces.PasswordClickedInterface
import kotlinx.android.synthetic.main.password_item.view.*
import java.util.*

class PasswordsAdapter(
    val context: Context,
    private val listener: PasswordClickedInterface

) :
    ListAdapter<Passwords, PasswordsAdapter.PasswordHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordHolder {

        return PasswordHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.password_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PasswordHolder, position: Int) {
        with(getItem(position)) {

            holder.passTitle.text = website
            holder.passDesc.text = emailAddress
            holder.passVerifiedIc.setImageResource(passStrengthIcon!!)
            holder.passIconRecycler.setImageResource(passIcon!!)
            holder.passIconRecycler.imageTintList =
                ContextCompat.getColorStateList(context, passColor!!)

            if (isLocked == true)
                holder.passSecurityIc.visibility = View.VISIBLE
            else
                holder.passSecurityIc.visibility = View.GONE
        }
    }

    fun getPasswordAt(position: Int) = getItem(position)

    inner class PasswordHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val passTitle: TextView = itemView.passTitle
        val passDesc: TextView = itemView.passDescription
        val passVerifiedIc: ImageView = itemView.passVerifiedIc
        val passSecurityIc: ImageView = itemView.passSecurityIc
        val passIconRecycler: ImageView = itemView.passIconRecycler

        //  Set onClick listener for the passwords
        init {
            itemView.setOnClickListener {
                listener.onPasswordClicked(getPasswordAt(bindingAdapterPosition))
            }
        }
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
                oldItem.passStrengthIcon == newItem.passStrengthIcon &&
                oldItem.passIcon == newItem.passIcon &&
                oldItem.passColor == newItem.passColor
    }
}