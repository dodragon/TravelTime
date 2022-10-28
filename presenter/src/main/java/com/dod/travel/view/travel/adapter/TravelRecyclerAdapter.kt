package com.dod.travel.view.travel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dod.travel.R
import com.dod.travel.databinding.ItemTravelBinding
import com.dod.data.model.TravelModel

class TravelRecyclerAdapter : PagingDataAdapter<TravelModel, TravelRecyclerAdapter.TravelHolder>(
    diffCallback
) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<TravelModel>() {
            override fun areItemsTheSame(oldItem: TravelModel, newItem: TravelModel): Boolean {
                return oldItem.travelId == newItem.travelId
            }

            override fun areContentsTheSame(oldItem: TravelModel, newItem: TravelModel): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelHolder =
        TravelHolder(ItemTravelBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            parent.context)

    override fun onBindViewHolder(holder: TravelHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
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