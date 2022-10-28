package com.dod.travel.view.group.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dod.travel.databinding.ItemGroupBinding
import com.dod.data.model.GroupModel
import com.dod.travel.view.travel.TravelActivity

class GroupRecyclerAdapter : PagingDataAdapter<GroupModel, GroupRecyclerAdapter.GroupHolder>(
    diffCallback
){

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<GroupModel>() {
            override fun areItemsTheSame(oldItem: GroupModel, newItem: GroupModel): Boolean {
                return oldItem.idx == newItem.idx
            }

            override fun areContentsTheSame(oldItem: GroupModel, newItem: GroupModel): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupHolder =
        GroupHolder(ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            parent.context)

    override fun onBindViewHolder(holder: GroupHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    inner class GroupHolder(
        private val binding: ItemGroupBinding,
        private val context: Context
    ) : ViewHolder(binding.root) {

        fun bind(data: GroupModel) {
            Log.e("group data", data.toString())
            binding.group = data
            binding.root.setOnClickListener {
                val intent = Intent(context, TravelActivity::class.java)
                intent.putExtra("groupId", data.idx)
                intent.putExtra("groupName", data.name)
                context.startActivity(intent)
            }
        }
    }
}