package com.dod.travel.view.share

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dod.travel.databinding.ActivitySharedBinding

class SharedActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySharedBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if(intent.action == Intent.ACTION_SEND) {
            if(intent.type!!.startsWith("text/")) {
                val text = intent.getStringExtra(Intent.EXTRA_TEXT)
                Log.e("text", text!!)
            }
        }
    }
}