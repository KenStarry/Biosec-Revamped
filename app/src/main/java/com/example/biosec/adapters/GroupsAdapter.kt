package com.example.biosec.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.biosec.R
import com.example.biosec.entities.Groups
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.group_card.view.*

class GroupsAdapter :  ListAdapter<Groups, GroupsAdapter.GroupsViewHolder>(diffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsViewHolder {
        return GroupsViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.group_card,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: GroupsViewHolder, position: Int) {
        with(getItem(position)) {

            holder.groupTitle.text = title
        }
    }

    class GroupsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var groupTitle = itemView.groupTitle
    }
}

private val diffCallback = object : DiffUtil.ItemCallback<Groups>() {
    override fun areItemsTheSame(oldItem: Groups, newItem: Groups): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Groups, newItem: Groups): Boolean {
        return oldItem.id == newItem.id &&
                oldItem.title == newItem.title &&
                oldItem.color == newItem.color &&
                oldItem.icon == newItem.icon
    }
}