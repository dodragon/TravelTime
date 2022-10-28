package com.dod.travel.view.share

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dod.travel.databinding.ActivityKakaoReceiveBinding
import com.dod.travel.view.group.GroupActivity
import com.dod.travel.view.user.login.LoginActivity

class KakaoShareReceiveActivity : AppCompatActivity() {

    private val binding by lazy { ActivityKakaoReceiveBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this,
        KakaoShareViewModel.KakaoShareFactory())[KakaoShareViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setObserver()
        initInviteGroup()
    }

    private fun initInviteGroup() {
        val groupId = intent.data?.getQueryParameter("groupId") ?: "0"
        val groupName = intent.data?.getQueryParameter("groupName") ?: ""
        val userName = intent.data?.getQueryParameter("userName") ?: ""

        binding.name.text = userName
        binding.group.text = groupName
        viewModel.getUserCnt(groupId.toLong())

        binding.cancelBtn.setOnClickListener {
            finish()
        }

        binding.acceptBtn.setOnClickListener {
            if(isLogin()){
                val userId = getSharedPreferences("user_info", MODE_PRIVATE).getLong("idx", 0)
                viewModel.groupJoin(groupId.toLong(), userId)
            }else {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("groupId", groupId.toLong())
                startActivity(intent)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setObserver() {
        viewModel.userCnt.observe(this) {
            binding.userEa.text = "총 ${it}명의 인원이\n이미 참여했어요!"
        }

        viewModel.messageData.observe(this) { message ->
            Toast.makeText(this, message.message, Toast.LENGTH_SHORT).show()
            if(!message.isError) {
                startActivity(Intent(this, GroupActivity::class.java))
                finish()
            }
        }
    }

    private fun isLogin(): Boolean {
        return !getSharedPreferences("user_info", MODE_PRIVATE)
            .getString("uid", "").equals("")
    }
}