package com.dod.travel.util

import androidx.recyclerview.widget.DiffUtil
import com.dod.data.model.DefaultData

class DiffUtilCallback(
    private val oldList: List<Any>,
    private val newList: List<Any>
) : DiffUtil.Callback(){

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return if(oldItem is com.dod.data.model.DefaultData && newItem is com.dod.data.model.DefaultData) {
            oldItem.idx == newItem.idx
        }else {
            false
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[oldItemPosition]
}