package com.dod.travel.view.user.join

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dod.data.model.MessageModel
import com.dod.travel.R
import com.dod.travel.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class JoinActivity : AppCompatActivity() {

    private val binding by lazy { ActivityJoinBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this,
        JoinViewModel.JoinFactory())[JoinViewModel::class.java] }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth
        binding.joinBtn.setOnClickListener {
            checkInfo()
        }
        setObserver()
    }

    private fun checkInfo() {
        val email = binding.email.text.toString()
        val pw = binding.pw.text.toString()
        val pwCheck = binding.pwCheck.text.toString()
        val name = binding.name.text.toString()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "이메일 형식에 맞게 입력해주세요.", Toast.LENGTH_SHORT).show()
        }else if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@!%*#?&.])[A-Za-z0-9\$@!%*#?&.]{8,20}\$", pw)) {
            Toast.makeText(this, "비밀번호는 특문+영문+숫자 조합의 8~20자로 설정해주세요.", Toast.LENGTH_SHORT).show()
        }else if(pw != pwCheck){
            Toast.makeText(this, "비밀번호와 비밀번호 확인이 동일하지 않습니다.", Toast.LENGTH_SHORT).show()
        }else if(name.isBlank() || name.isEmpty()){
            Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
        }else {
            join(email, pw, name)
        }
    }

    private fun join(email: String, pw: String, name: String){
        auth.createUserWithEmailAndPassword(email, pw)
            .addOnSuccessListener {
                val user = it.user
                if(user != null) {
                    val userModel = com.dod.data.model.UserModel(
                        0,
                        email,
                        pw,
                        user.uid,
                        name,
                        arrayListOf(),
                        "",
                        MessageModel(false, ""),
                        ""
                    )

                    viewModel.userJoin(userModel)
                }else {
                    Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun setObserver() {
        viewModel.messageData.observe(this) {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            if(!it.isError) {
                finish()
            }
        }
    }
}