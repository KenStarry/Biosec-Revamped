package com.example.biosec.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.biosec.R
import com.example.biosec.interfaces.OnItemClickListener
import kotlinx.android.synthetic.main.color_picker_item.view.*

class ColorPickerAdapter(
    private val context: Context,
    private val listener: OnItemClickListener

) : RecyclerView.Adapter<ColorPickerAdapter.ColorViewHolder>() {

    val colorsArray = intArrayOf(
        R.color.user_red_1, R.color.user_blue_1, R.color.user_green_1, R.color.user_purple_1,
        R.color.user_red_2, R.color.user_blue_2, R.color.user_green_2, R.color.user_purple_2,
        R.color.user_red_3, R.color.user_blue_3, R.color.user_green_3, R.color.user_purple_3,
        R.color.user_red_4, R.color.user_blue_4, R.color.user_green_4, R.color.user_purple_4,
        R.color.user_red_5, R.color.user_blue_5, R.color.user_green_5, R.color.user_purple_5
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.color_picker_item,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        with(getItemId(position)) {
            holder.col.setCardBackgroundColor(ContextCompat.getColor(context, colorsArray[position]))
        }
    }

    override fun getItemCount(): Int {
        return colorsArray.size
    }

    inner class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{

        val col = itemView.col

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val userCol = colorsArray[bindingAdapterPosition]

            if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                listener.onItemClick(userCol)
            }
        }
    }
}