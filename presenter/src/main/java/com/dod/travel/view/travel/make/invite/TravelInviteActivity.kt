package com.dod.travel.view.travel.make.invite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dod.data.model.UserModel
import com.dod.travel.databinding.ActivityTravelInviteBinding
import com.dod.travel.view.travel.TravelViewModel
import com.dod.travel.view.travel.make.invite.adapter.TravelInviteAdapter
import com.dod.travel.view.travel.make.invite.adapter.UsersSelectListener

class TravelInviteActivity : AppCompatActivity(), UsersSelectListener {

    private val binding by lazy { ActivityTravelInviteBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this,
        TravelViewModel.TravelFactory())[TravelViewModel::class.java] }

    private lateinit var inviteAdapter: TravelInviteAdapter

    private var groupId = 0L
    private lateinit var groupName: String

    private val selectedUsers = mutableListOf<UserModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        groupId = intent.getLongExtra("groupId", 0L)
        groupName = intent.getStringExtra("groupName")!!

        initView()
        setObserver()
    }

    private fun initView() {
        binding.title.text = groupName

        inviteAdapter = TravelInviteAdapter(Glide.with(this), this,
            getSharedPreferences("user_info", MODE_PRIVATE).getLong("idx", 0L))
        binding.recycler.run {
            layoutManager = LinearLayoutManager(this@TravelInviteActivity, LinearLayoutManager.VERTICAL, false)
            adapter = inviteAdapter
        }

        binding.selectBtn.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("idxList", getUserIdxList())
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun getUserIdxList(): LongArray {
        val userIdxList = LongArray(selectedUsers.size)
        for((i, um) in selectedUsers.withIndex()){
            userIdxList[i] = um.idx
        }

        return userIdxList
    }

    override fun addUser(user: UserModel) {
        if(!selectedUsers.contains(user)){
            selectedUsers.add(user)
        }
    }

    override fun deleteUser(user: UserModel) {
        if(selectedUsers.contains(user)){
            selectedUsers.remove(user)
        }
    }

    private fun setObserver() {
        viewModel.getGroupUserList(groupId).observe(this) { userList ->
            inviteAdapter.submitData(this.lifecycle, userList)
        }
    }
}