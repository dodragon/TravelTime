package com.dod.travel.view.user.setting

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dod.data.model.MessageModel
import com.dod.data.model.UserModel
import com.dod.travel.databinding.ActivitySettingBinding
import com.dod.travel.util.ImageUtil
import com.dod.travel.view.user.login.LoginActivity
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.util.*

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

        binding.profileImage.setOnClickListener {
            permissionCheck()
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
        Log.e("프로필", url)
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

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            "image/*"
        )
        imageLauncher.launch(intent)
    }

    private val imageLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == RESULT_OK && it.data != null) {
                val imageUri = it.data?.data
                try{
                    if(imageUri != null) {
                        val bitmap = ImageUtil.resizeBitMap(imageUri, 360, applicationContext)

                        if(bitmap != null) {
                            Glide.with(this)
                                .load(bitmap)
                                .circleCrop()
                                .into(binding.profileImage)

                            viewModel.upLoadImage(
                                bitmapToJpg(bitmap),
                                "user",
                                System.currentTimeMillis().toString() + UUID.randomUUID() + ".jpg",
                                object : SettingViewModel.ImageUploadListener {
                                    override fun success() {
                                        Toast.makeText(this@SettingActivity, "프로필 이미지 변경 완료", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            )
                        }
                    }
                }catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    private fun bitmapToJpg(bitmap: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
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
            pickImage()
        }
    }

    private var permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            pickImage()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            Toast.makeText(this@SettingActivity, "설정에서 저장소 권한을 확인해주세요", Toast.LENGTH_SHORT).show()
        }
    }
}