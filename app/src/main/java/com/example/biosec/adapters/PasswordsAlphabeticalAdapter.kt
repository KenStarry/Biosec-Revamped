package com.example.biosec.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.biosec.R
import com.example.biosec.activities.MainActivity
import com.example.biosec.entities.Passwords
import com.example.biosec.viewmodels.PasswordsViewModel
import java.util.ArrayList

class PasswordsAlphabeticalAdapter(
    context: Context,
    private val alphabetList: List<Char>,
    private val passwords: List<Passwords>

) : RecyclerView.Adapter<PasswordsAlphabeticalAdapter.PassViewHolder>() {

    private val parentAdapter = PasswordsAdapter(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassViewHolder {

        return PassViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.password_alphabetical_item,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: PassViewHolder, position: Int) {

        holder.alphabetTitle.text = alphabetList.get(position).toString()

        val mutableList = mutableListOf<Passwords>()

        passwords.forEach { pass ->

            if (pass.website!!.toString().get(0) == alphabetList.get(position)) {

                mutableList.add(pass)
            }
            parentAdapter.submitList(mutableList)
        }

        holder.childRecyclerView.adapter = parentAdapter
        holder.childRecyclerView.layoutManager = LinearLayoutManager(
            holder.childRecyclerView.context,
            LinearLayoutManager.VERTICAL,
            false
        )


    }

    class PassViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val alphabetTitle: TextView = itemView.findViewById(R.id.alphabetTitle)
        val childRecyclerView: RecyclerView = itemView.findViewById(R.id.childRecyclerView)
    }

    override fun getItemCount(): Int {
        return alphabetList.size
    }
}