package com.dod.travel.view.travel.make.invite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import com.dod.data.model.UserModel
import com.dod.travel.databinding.ItemUserSelectBinding

class TravelInviteAdapter(
    private val glide: RequestManager,
    private val listener: UsersSelectListener,
    private val myId: Long
) : PagingDataAdapter<UserModel, TravelInviteAdapter.UserHolder>(
    diffCallback
) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder =
        UserHolder(ItemUserSelectBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            glide, listener, myId)

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    inner class UserHolder(
        private val binding: ItemUserSelectBinding,
        private val glide: RequestManager,
        private val listener: UsersSelectListener,
        private val myId: Long
    ) : ViewHolder(binding.root) {

        fun bind(data: UserModel) {
            binding.user = data
            glide.load(data.profile).circleCrop().into(binding.profileImage)

            binding.root.setOnClickListener {
                if(binding.checkbox.isChecked) {
                    binding.checkbox.isChecked = false
                    listener.deleteUser(data)
                }else {
                    binding.checkbox.isChecked = true
                    listener.addUser(data)
                }
            }

            if(data.idx == myId) {
                binding.root.performClick()
            }
        }
    }
}