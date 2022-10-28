package com.dod.travel.view.group.make

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dod.travel.R
import com.dod.travel.databinding.ActivityGroupMakeBinding

class GroupMakeActivity : AppCompatActivity() {

    private val binding by lazy { ActivityGroupMakeBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this,
        GroupMakeViewModel.GroupMakeFactory())[GroupMakeViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        setObserver()
    }

    private fun initView(){
        binding.addBtn.setOnClickListener {
            val name = binding.name.text.toString()
            val description = binding.description.text.toString()
            val userIdx = userIdxGet()

            if(userIdx != 0L && nameCheck(name)){
                viewModel.makeGroup(name, description, userIdx)
            }
        }
    }

    private fun setObserver(){
        viewModel.messageData.observe(this) { message ->
            Toast.makeText(this, message.message, Toast.LENGTH_SHORT).show()
            if(!message.isError) {
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    private fun nameCheck(name: String?): Boolean = !(name == null || name.isEmpty() || name.isBlank())

    private fun userIdxGet(): Long = getSharedPreferences("user_info", MODE_PRIVATE)
        .getLong("idx", 0L)
}