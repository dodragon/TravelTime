package com.dod.travel.view.group.invite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dod.travel.databinding.ActivityInviteBinding

class InviteActivity : AppCompatActivity() {

    private val binding by lazy { ActivityInviteBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}