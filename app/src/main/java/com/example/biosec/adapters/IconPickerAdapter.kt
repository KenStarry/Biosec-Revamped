package com.example.biosec.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.biosec.R
import com.example.biosec.interfaces.IconClickedInterface
import kotlinx.android.synthetic.main.icon_picker_item.view.*

class IconPickerAdapter(
    private val context: Context,
    private val listener: IconClickedInterface

) : RecyclerView.Adapter<IconPickerAdapter.IconViewHolder>() {

    private val iconsArray = intArrayOf(
        R.drawable.ic__lock, R.drawable.ic_dashboard, R.drawable.ic_lock_open,
        R.drawable.ic_archive, R.drawable.ic_delete_forever, R.drawable.ic_grain
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {

        return IconViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.icon_picker_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        with(getItemId(position)) {

            holder.icon.setImageResource(iconsArray[position])
        }
    }

    override fun getItemCount(): Int {
        return iconsArray.size
    }

    inner class IconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon = itemView.userIcon

        init {
            itemView.setOnClickListener {

                val icon = iconsArray[bindingAdapterPosition]

                if (bindingAdapterPosition != RecyclerView.NO_POSITION)
                    listener.onIconClicked(icon)
            }
        }
    }
}