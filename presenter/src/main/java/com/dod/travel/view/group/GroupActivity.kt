package com.dod.travel.view.group

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dod.travel.databinding.ActivityMainBinding
import com.dod.travel.view.group.adapter.GroupRecyclerAdapter
import com.dod.travel.view.group.make.GroupMakeActivity
import com.dod.travel.view.user.setting.SettingActivity

/*===========================================
* @className    GroupActivity.kt
* @author       dohyeon.kim
* @since        2022-10-21 오후 3:37
* @description  Team List
============================================ */
class GroupActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this,
        GroupViewModel.GroupFactory())[GroupViewModel::class.java] }

    private lateinit var groupAdapter: GroupRecyclerAdapter

    private var backPressTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        this.onBackPressedDispatcher.addCallback(backPressCallback)
        initView()

        setObserver()
    }

    private val backPressCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val tempTime = System.currentTimeMillis()
            val intervalTime = tempTime - backPressTime
            val finishIntervalTime = 3000L

            if(intervalTime in 0..finishIntervalTime) {
                finish()
            }else {
                backPressTime = tempTime
                Toast.makeText(this@GroupActivity, "뒤로가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initView() {
        groupAdapter = GroupRecyclerAdapter()
        binding.recycler.run {
            layoutManager = LinearLayoutManager(this@GroupActivity, LinearLayoutManager.VERTICAL, false)
            adapter = groupAdapter
        }

        binding.swipe.setOnRefreshListener {
            groupAdapter.refresh()
            binding.swipe.isRefreshing = false
        }

        binding.addBtn.setOnClickListener {
            makeGroupResultLauncher.launch(Intent(this, GroupMakeActivity::class.java))
        }

        binding.profileImage.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }

        profileImgSet()
    }

    private val makeGroupResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            groupAdapter.refresh()
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
        val userIdx = getSharedPreferences("user_info", MODE_PRIVATE)
            .getLong("idx", 0)

        viewModel.getGroupList(userIdx).observe(this) {
            groupAdapter.submitData(this.lifecycle, it)
        }
    }
}