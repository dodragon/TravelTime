package com.dod.travel.view.travel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dod.data.model.UserModel
import com.dod.travel.databinding.ActivityTravelBinding
import com.dod.travel.util.sns.KakaoShare
import com.dod.travel.view.travel.adapter.TravelRecyclerAdapter
import com.dod.travel.view.travel.adapter.UserRecyclerAdapter
import com.dod.travel.view.travel.make.TravelMakeActivity
import com.dod.travel.view.user.setting.SettingActivity

class TravelActivity : AppCompatActivity() {

    private val binding by lazy { ActivityTravelBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this,
        TravelViewModel.TravelFactory())[TravelViewModel::class.java] }

    private var groupId = 0L
    private var groupName = ""
    private lateinit var travelAdapter: TravelRecyclerAdapter
    private lateinit var userAdapter: UserRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        groupId = intent.getLongExtra("groupId", 0L)
        groupName = intent.getStringExtra("groupName")!!

        setObserver()
        initView()
    }

    private fun initView() {
        binding.title.text = groupName
        profileImgSet()

        userAdapter = UserRecyclerAdapter(Glide.with(this))
        binding.userRecycler.run {
            layoutManager = LinearLayoutManager(this@TravelActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = userAdapter
        }

        travelAdapter = TravelRecyclerAdapter()
        binding.recycler.run {
            layoutManager = LinearLayoutManager(this@TravelActivity, LinearLayoutManager.VERTICAL, false)
            adapter = travelAdapter
        }

        binding.swipe.setOnRefreshListener {
            //TODO: Refresh
            binding.swipe.isRefreshing = false
        }

        binding.addBtn.setOnClickListener {
            val intent = Intent(this, TravelMakeActivity::class.java)
            intent.putExtra("groupId", groupId)
            intent.putExtra("groupName", groupName)
            makeLauncher.launch(intent)
        }

        binding.inviteBtn.setOnClickListener {
            val userName = getSharedPreferences("user_info", MODE_PRIVATE)
                .getString("name", "")

            val data = mutableMapOf<String, String>()
            data["groupId"] = groupId.toString()
            data["groupName"] = groupName
            data["userName"] = userName!!

            KakaoShare.share(KakaoShare.feed(userName, data), this)
        }

        binding.profileImage.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }
    }

    private val makeLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            //TODO: refresh
        }
    }

    private fun profileImgSet() {
        val profile = getSharedPreferences("user_info", MODE_PRIVATE)
            .getString("profile", "")
        if(!profile.equals("")){
            Glide.with(this)
                .load(profile)
                .circleCrop()
                .into(binding.profileImage)
        }
    }

    private fun setObserver() {
        viewModel.getGroupUserList(groupId).observe(this) { userList ->
            userAdapter.submitData(this.lifecycle, userList)
        }

        viewModel.getTravelList(groupId).observe(this) { travelList ->

        }
    }
}