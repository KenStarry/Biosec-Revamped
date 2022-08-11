package com.example.biosec.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.biosec.R
import com.example.biosec.entities.Archives

class ArchivesAdapter(
    private val context: Context

) : ListAdapter<Archives, ArchivesAdapter.ArchivesViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchivesViewHolder {

        return ArchivesViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.password_item,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ArchivesViewHolder, position: Int) {
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

    fun getArchiveAt(position: Int) = getItem(position)

    class ArchivesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val passTitle: TextView = itemView.findViewById(R.id.passTitle)
        val passDesc: TextView = itemView.findViewById(R.id.passDescription)
        val passVerifiedIc: ImageView = itemView.findViewById(R.id.passVerifiedIc)
        val passSecurityIc: ImageView = itemView.findViewById(R.id.passSecurityIc)
        val passIconRecycler: ImageView = itemView.findViewById(R.id.passIconRecycler)
    }
}

private val diffCallback = object : DiffUtil.ItemCallback<Archives>() {

    override fun areItemsTheSame(oldItem: Archives, newItem: Archives): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Archives, newItem: Archives): Boolean {
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