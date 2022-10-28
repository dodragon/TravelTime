package com.dod.travel.view.travel.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dod.travel.databinding.ActivityTravelDetailBinding

class TravelDetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityTravelDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}