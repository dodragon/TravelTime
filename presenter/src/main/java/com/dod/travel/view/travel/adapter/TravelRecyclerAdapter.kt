package com.dod.travel.view.travel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dod.travel.R
import com.dod.travel.databinding.ItemTravelBinding
import com.dod.data.model.TravelModel
import com.dod.travel.util.DiffUtilCallback

class TravelRecyclerAdapter : RecyclerView.Adapter<TravelRecyclerAdapter.TravelHolder>() {

    private val tripList = mutableListOf<TravelModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelHolder =
        TravelHolder(ItemTravelBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            parent.context)

    override fun onBindViewHolder(holder: TravelHolder, position: Int) {
        holder.bind(tripList[position])
    }

    override fun getItemCount(): Int  = tripList.size

    fun updateList(items: List<TravelModel>, isAdd: Boolean) {
        items.let {
            val diffCallback = DiffUtilCallback(tripList, items)
            val diffResult = DiffUtil.calculateDiff(diffCallback)

            tripList.run {
                if (isAdd){
                    clear()
                }
                addAll(items)
                diffResult.dispatchUpdatesTo(this@TravelRecyclerAdapter)
            }
        }
    }

    inner class TravelHolder(
        private val binding: ItemTravelBinding,
        private val context: Context
    ): ViewHolder(binding.root) {

        fun bind(data: TravelModel) {
            binding.travel = data
            binding.root.setOnClickListener {
                //TODO: go Detail
            }

            if(data.isEnd) {
                binding.root.setBackgroundColor(context.getColor(R.color.light_gray))
            }else {
                binding.root.setBackgroundColor(context.getColor(R.color.white))
            }
        }
    }
}