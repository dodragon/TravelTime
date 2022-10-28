package com.dod.travel.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dod.travel.databinding.ActivitySplashBinding
import com.dod.travel.view.group.GroupActivity
import com.dod.travel.view.user.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        goMain()
    }

    private fun userCheck() {
        val spf = getSharedPreferences("login", MODE_PRIVATE)
        if(spf.getBoolean("auto_login", false)) {
            startActivity(Intent(this, GroupActivity::class.java))
        }else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }

    private fun goMain() {
        Handler(Looper.getMainLooper()).postDelayed({
            userCheck()
        }, 1500)
    }
}