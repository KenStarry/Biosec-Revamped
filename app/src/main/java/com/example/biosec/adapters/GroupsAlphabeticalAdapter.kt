package com.example.biosec.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.biosec.R
import kotlinx.android.synthetic.main.group_alphabetical_card.view.*

class GroupsAlphabeticalAdapter(
    private val context: Context,
    private val array: Array<String>

) : RecyclerView.Adapter<GroupsAlphabeticalAdapter.AlphabetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlphabetViewHolder {
        return AlphabetViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.group_alphabetical_card,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: AlphabetViewHolder, position: Int) {

        holder.letter.text = array[position]
    }

    override fun getItemCount(): Int {
        return array.size
    }

    inner class AlphabetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val letter: TextView = itemView.letter
    }
}