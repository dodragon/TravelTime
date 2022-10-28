package com.dod.travel.view.travel.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import com.dod.travel.databinding.ItemUserBinding
import com.dod.data.model.UserModel
import com.dod.travel.util.DiffUtilCallback

class UserRecyclerAdapter (
    private val glide: RequestManager
) : PagingDataAdapter<UserModel, UserRecyclerAdapter.UserViewHolder>(
    diffCallback
){

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<UserModel>() {
            override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem.idx == newItem.idx
            }

            override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            glide)

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    inner class UserViewHolder(
        private val binding: ItemUserBinding,
        private val glide: RequestManager
    ) : ViewHolder(binding.root) {

        fun bind(data: UserModel) {
            Log.e("user data", data.toString())
            binding.name.text = data.name
            if(data.profile != "" && data.profile.startsWith("http")) {
                glide.load(data.profile).circleCrop().into(binding.profileImage)
            }
        }
    }
}