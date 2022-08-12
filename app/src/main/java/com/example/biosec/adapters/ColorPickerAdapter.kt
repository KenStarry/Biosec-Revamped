package com.example.biosec.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.biosec.R
import kotlinx.android.synthetic.main.color_picker_item.view.*

class ColorPickerAdapter(
    private val context: Context,
    private val listener: OnItemClickListener

) : RecyclerView.Adapter<ColorPickerAdapter.ColorViewHolder>() {

    val colorsArray = intArrayOf(
        R.color.weak_pass, R.color.medium_pass,
        R.color.strong_pass, R.color.blue,
        R.color.archive, R.color.blue_light,
        R.color.pink, R.color.text_black_100,
        R.color.text_black_900
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

    interface OnItemClickListener {
        fun onItemClick(userCol: Int)
    }
}