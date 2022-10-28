package com.dod.travel.view.user.setting

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dod.data.model.MessageModel
import com.dod.data.model.UserModel
import com.dod.travel.databinding.ActivitySettingBinding
import com.dod.travel.view.user.login.LoginActivity
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

class SettingActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySettingBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this,
        SettingViewModel.SettingFactory())[SettingViewModel::class.java] }

    private lateinit var userSpf: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        userSpf = getSharedPreferences("user_info", MODE_PRIVATE)

        setObserver()
        initView()
    }

    private fun initView() {
        binding.user = userGet()

        binding.logout.setOnClickListener {
            makeAlert(
                title = "로그아웃",
                message = "로그아웃 하시겠습니까?"
            ){ dialog, _ ->
                userSpf.edit().clear().apply()
                dialog.dismiss()

                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
            }.show()
        }

        binding.pwChange.setOnClickListener {
            val intent = Intent(this, PasswordActivity::class.java)
            intent.putExtra("userId", userGet().idx)
            startActivity(intent)
        }

        binding.nameSave.setOnClickListener {
            val user = userGet()
            user.name = binding.nameEdit.text.toString()
            viewModel.updateUser(user)
        }

        binding.license.setOnClickListener {
            OssLicensesMenuActivity.setActivityTitle("License")
            startActivity(Intent(this, OssLicensesMenuActivity::class.java))
        }
    }

    private fun setObserver() {
        viewModel.userData.observe(this) { user ->
            Toast.makeText(this, user.message.message, Toast.LENGTH_SHORT).show()
            if(!user.message.isError){
                binding.user = user
                userSave(user)
                profileImgSet(user.profile)
            }
        }
    }

    private fun userGet(): UserModel {
        return UserModel(
            idx = userSpf.getLong("idx", 0L),
            email = userSpf.getString("email", "")!!,
            name = userSpf.getString("name", "")!!,
            profile = userSpf.getString("profile", "")!!,
            uid = userSpf.getString("uid", "")!!,
            password = "",
            message = MessageModel(false, ""),
            teams = arrayListOf(),
            joinedAt = ""
        )
    }

    private fun userSave(user: UserModel) {
        userSpf.edit()
            .putString("email", user.email)
            .putLong("idx", user.idx)
            .putString("uid", user.uid)
            .putString("name", user.name)
            .putString("profile", user.profile)
            .apply()
    }

    private fun profileImgSet(url: String) {
        if(url != ""){
            Glide.with(this)
                .load(url)
                .circleCrop()
                .into(binding.profileImage)
        }
    }

    private fun makeAlert(title: String, message: String, listener: DialogInterface.OnClickListener): AlertDialog {
        return AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("확인", listener)
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }
}