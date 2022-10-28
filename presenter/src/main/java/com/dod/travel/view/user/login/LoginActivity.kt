package com.dod.travel.view.user.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.dod.data.model.UserModel
import com.dod.travel.databinding.ActivityLoginBinding
import com.dod.travel.util.sns.EmailLogin
import com.dod.travel.util.sns.GoogleLogin
import com.dod.travel.util.sns.LoginListener
import com.dod.travel.view.group.GroupActivity
import com.dod.travel.view.user.join.JoinActivity
import com.google.android.gms.auth.api.Auth

/*===========================================
* @className    LoginActivity.kt
* @author       dohyeon.kim
* @since        2022-10-21 오후 3:37
* @description  Login
============================================ */
class LoginActivity : AppCompatActivity(), LoginListener {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this,
        LoginViewModel.LoginFactory())[LoginViewModel::class.java] }

    private lateinit var googleLogin: GoogleLogin
    private lateinit var emailLogin: EmailLogin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        googleLogin = GoogleLogin(this, googleLauncher, this)
        emailLogin = EmailLogin(this, this)

        // 구글로그인
        binding.googleLogin.setOnClickListener {
            googleLogin.googleLogin()
        }

        // 이메일 로그인
        binding.emailLogin.setOnClickListener {
            emailLogin.login(
                binding.email.text.toString(),
                binding.pw.text.toString()
            )
        }

        // 회원가입 이동
        binding.joinBtn.setOnClickListener {
            startActivity(Intent(this, JoinActivity::class.java))
        }

        setObserver()
    }

    private val googleLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(result.data!!)
        if(signInResult!!.isSuccess) {
            val account = signInResult.signInAccount
            googleLogin.requestAuth(account!!)
        }else {
            Log.e("Google Login", "Failed")
        }
    }

    override fun checkUser(email: String, pw: String, uid: String, name: String, isSns: Boolean) {
        if(isSns){
            viewModel.snsLogin(email, uid, name)
        }else {
            viewModel.login(email, pw, uid)
        }
    }

    private fun setObserver() {
        viewModel.userData.observe(this) { user ->
            if(!user.message.isError) {
                userSave(user)

                val groupId = intent.getLongExtra("groupId", 0L)
                if(groupId != 0L) {
                    viewModel.groupJoin(groupId, user.idx)
                }else {
                    startActivity(Intent(this, GroupActivity::class.java))
                    finish()
                }
            }else {
                Toast.makeText(this, user.message.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.messageData.observe(this) { message ->
            Toast.makeText(this, message.message, Toast.LENGTH_SHORT).show()
            if(!message.isError) {
                startActivity(Intent(this, GroupActivity::class.java))
                finish()
            }
        }
    }

    private fun userSave(user: UserModel) {
        val spf = getSharedPreferences("user_info", MODE_PRIVATE)
        spf.edit()
            .putString("email", user.email)
            .putLong("idx", user.idx)
            .putString("uid", user.uid)
            .putString("name", user.name)
            .putString("profile", user.profile)
            .apply()

        autoLoginCheck(binding.autoLogin.isChecked)
    }

    private fun autoLoginCheck(isAutoLogin: Boolean) {
        val spf = getSharedPreferences("login", MODE_PRIVATE)
        spf.edit().putBoolean("auto_login", isAutoLogin).apply()
    }
}