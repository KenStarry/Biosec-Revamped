package com.example.biosec.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.biosec.R
import kotlinx.android.synthetic.main.view_password_item.view.*

class ViewPasswordAdapter(
    private val context: Context,
    private val passwordColumns: Array<String>,
    private val passwordIcons: IntArray,
    private val passwordMap: MutableMap<Int, String?>,
    private val passwordColor: Int

) : RecyclerView.Adapter<ViewPasswordAdapter.ViewPassViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPassViewHolder {
        return ViewPassViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.view_password_item,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ViewPassViewHolder, position: Int) {
        with (getItemId(position)) {

            val animation: Animation = AnimationUtils.loadAnimation(holder.itemView.context, android.R.anim.slide_in_left)

            holder.colName.text = passwordColumns.get(position)
            holder.colValue.text = passwordMap.get(position)
            holder.icon.setImageResource(passwordIcons.get(position))
            holder.line.backgroundTintList = ContextCompat.getColorStateList(context, passwordColor)

            holder.itemView.startAnimation(animation)
        }
    }

    override fun getItemCount(): Int {
        return passwordMap.size
    }

    class ViewPassViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val icon: ImageView = itemView.viewPassItemIcon
        val line: View = itemView.viewPassItemLine
        val colName: TextView = itemView.viewPassItemColumn
        val colValue: TextView = itemView.viewPassItemValue
    }
}