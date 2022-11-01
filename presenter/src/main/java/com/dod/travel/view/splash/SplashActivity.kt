package com.dod.travel.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.dod.travel.databinding.ActivitySplashBinding
import com.dod.travel.view.group.GroupActivity
import com.dod.travel.view.user.login.LoginActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        permissionCheck()
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

    private fun permissionCheck() {
        if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            TedPermission.create()
                .setPermissionListener(permissionListener)
                .setPermissions(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .check()
        }else {
            goMain()
        }
    }

    private var permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            goMain()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            Toast.makeText(this@SplashActivity, "설정에서 저장소 권한을 확인해주세요", Toast.LENGTH_SHORT).show()
            goMain()
        }
    }
}